import re
from datetime import datetime, timezone
from typing import Any, Set

from sqlalchemy import Boolean, Column, DateTime, ForeignKey, Integer, String, Text
from sqlalchemy.orm import Mapped, mapped_column, relationship
from werkzeug.security import check_password_hash, generate_password_hash

from blog.config import get_config
from blog.extens import db
from blog.utlis import serialize_datetime

config = get_config()

article_tag_association = db.Table(
    "article_tag_association",
    Column("article_id", Integer, ForeignKey("article.id"), primary_key=True),
    Column("tag_id", Integer, ForeignKey("tag.id"), primary_key=True),
)


def filter_string(input_string: str):
    pattern = re.compile(r"[^a-zA-Z0-9_\-]|^.{0,1}$|^.{101,}$")
    filtered_string = re.sub(pattern, "", input_string)
    return filtered_string


def get_auto_slug(prefix: str = "Blog_article_") -> str:
    return prefix + datetime.now().strftime("%Y%m%d%H%M%S")


class User(db.Model):
    id: Mapped[int] = mapped_column(Integer, primary_key=True, autoincrement=True)
    username: Mapped[str] = mapped_column(String(255), unique=True)
    password_hash: Mapped[str] = mapped_column(String(255))
    nickname: Mapped[str] = mapped_column(String(255))
    description: Mapped[str] = mapped_column(Text, default="")
    registered_at: Mapped[datetime] = mapped_column(DateTime)
    last_login_at: Mapped[datetime] = mapped_column(DateTime)
    token_validity_period: Mapped[int] = mapped_column(Integer, default=604800)
    articles = relationship("Article", back_populates="author")
    series = relationship("Series", back_populates="author")
    tags = relationship("Tag", back_populates="author")

    @classmethod
    def create(cls, username: str, password: str) -> "User":
        new_user = User(
            username=username,
            password_hash=generate_password_hash(password),
            nickname=username.title(),
            registered_at=datetime.utcnow(),
            last_login_at=datetime.utcnow(),
        )  # type: ignore
        db.session.add(new_user)
        db.session.commit()
        return User.query.get(new_user.id)  # type: ignore

    def validate_password(self, password: str) -> bool:
        return check_password_hash(self.password_hash, password)

    def extend_validity_period(self) -> None:
        self.token_validity_period += config.VALIDITY_INCREMENT
        db.session.add(self)
        db.session.commit()

    def to_dict(self) -> dict[str, Any]:
        return dict(
            id=self.id,
            username=self.username,
            nickname=self.nickname,
            description=self.description,
            registeredAt=serialize_datetime(
                self.registered_at.astimezone(timezone.utc)
            ),
            lastLoginAt=serialize_datetime(self.last_login_at.astimezone(timezone.utc)),
            tokenValidityPeriod=self.token_validity_period,
        )

class Tag(db.Model):
    id: Mapped[int] = mapped_column(Integer, primary_key=True, autoincrement=True)
    name: Mapped[str] = mapped_column(String(255), unique=True)
    articles: Mapped[Set["Article"]] = relationship(
        secondary=article_tag_association, back_populates="tags"
    )
    author_id: Mapped[int] = mapped_column(Integer, ForeignKey("user.id"))
    author: Mapped["User"] = relationship("User", back_populates="tags")

    @classmethod
    def create(cls, author: User, *names: str) -> list["Tag"]:
        tags = set(names)
        for tag in tags:
            if Tag.query.filter_by(name=tag).first():
                continue
            new_tag = Tag(author=author, name=tag)  # type: ignore
            db.session.add(new_tag)
        db.session.commit()
        return [Tag.query.filter_by(name=name).first() for name in tags]  # type: ignore

    def to_dict(self) -> dict[str, str | int]:
        return dict(id=self.id, name=self.name)


class Series(db.Model):
    id: Mapped[int] = mapped_column(Integer, primary_key=True, autoincrement=True)
    name: Mapped[str] = mapped_column(String(255))
    cover: Mapped[str] = mapped_column(String(255))
    created_at: Mapped[datetime] = mapped_column(DateTime, default=datetime.utcnow())
    author_id: Mapped[int] = mapped_column(Integer, ForeignKey("user.id"))
    author: Mapped["User"] = relationship("User", back_populates="series")
    articles: Mapped["list[Article]"] = relationship("Article", back_populates="series")

    @classmethod
    def create(cls, author: User, name: str, cover: str) -> "Series":
        new_series = Series(author=author, name=name, cover=cover)  # type:ignore
        db.session.add(new_series)
        db.session.commit()
        return Series.query.get(new_series.id)  # type:ignore

    def update(self, name: str, cover: str) -> "Series":
        self.name = name
        self.cover = cover
        db.session.add(self)
        db.session.commit()
        return Series.query.get(self.id)  # type:ignore

    def delete(self) -> None:
        db.session.delete(self)
        db.session.commit()

    def to_dict(self):
        return dict(
            id=self.id,
            name=self.name,
            cover=self.cover,
            authorId=self.author_id,
            createdAt=serialize_datetime(self.created_at),
        )

    def __repr__(self) -> str:
        return f"Series <{self.id}>"


class Article(db.Model):
    id: Mapped[int] = mapped_column(Integer, primary_key=True, autoincrement=True)
    title: Mapped[str] = mapped_column(String(255), default="")
    slug: Mapped[str] = mapped_column(String(255), unique=True)
    body: Mapped[str] = mapped_column(Text, default="")
    created_at: Mapped[datetime] = mapped_column(DateTime, default=datetime.utcnow())
    updated_at: Mapped[datetime] = mapped_column(DateTime, default=datetime.utcnow())
    is_published: Mapped[bool] = mapped_column(Boolean, default=False)
    published_at: Mapped[datetime] = mapped_column(DateTime, default=datetime.utcnow())
    series_id: Mapped[int] = mapped_column(
        Integer, ForeignKey("series.id"), nullable=True
    )
    series = relationship("Series", back_populates="articles")
    author_id: Mapped[int] = mapped_column(Integer, ForeignKey("user.id"))
    author = relationship("User", back_populates="articles")
    tags: Mapped[Set["Tag"]] = relationship(
        secondary=article_tag_association, back_populates="articles"
    )

    @classmethod
    def create(cls, author: User) -> "Article":
        slug = get_auto_slug()
        new_article = Article(author=author, slug=slug)  # type: ignore
        db.session.add(new_article)
        db.session.commit()
        return Article.query.get(new_article.id)  # type: ignore

    def update(
        self,
        title: str,
        body: str,
        slug: str,
        published_at: datetime,
        tags: list[Tag],
        is_published: bool = False,
        series: Series | None = None,
    ) -> "Article":  # type: ignore
        self.title = title
        self.body = body
        self.slug = slug
        self.published_at = published_at
        self.is_published = is_published
        if series:
            self.series = series
        self.tags = set(tags)
        self.updated_at = datetime.utcnow()
        db.session.add(self)
        db.session.commit()
        return Article.query.get(self.id)  # type: ignore

    def update_card(
        self,
        is_published: bool | None = None,
        tags: list[Tag] | None = None,
    ) -> "Article":  # type: ignore
        if not (is_published is None):
            self.is_published = is_published
        if not (tags is None):
            self.tags = set(tags)
        db.session.add(self)
        db.session.commit()
        return Article.query.get(self.id)  # type: ignore

    def delete(self) -> None:
        db.session.delete(self)
        db.session.commit()

    def to_dict(self):
        return dict(
            id=self.id,
            title=self.title,
            body=self.body,
            slug=self.slug,
            isPublished=self.is_published,
            publishedAt=serialize_datetime(self.published_at),
            createdAt=serialize_datetime(self.created_at),
            updatedAt=serialize_datetime(self.updated_at),
            seriesId=self.series_id or 0,
            authorId=self.author_id,
            tags=[tag.name for tag in self.tags],  # type: ignore
        )

    def __repr__(self) -> str:
        return f"Article <{self.id}>"
