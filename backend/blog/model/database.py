from datetime import datetime
from typing import Any

from sqlalchemy import Boolean, DateTime, ForeignKey, Integer, String, Text, Column
from sqlalchemy.orm import Mapped, mapped_column, relationship
from werkzeug.security import check_password_hash, generate_password_hash

from blog.extens import db

post_tag_association = db.Table(
    "post_tag_association",
    Column("post_id", Integer, ForeignKey("post.id"), primary_key=True),
    Column("tag_id", Integer, ForeignKey("tag.id"), primary_key=True),
)


class User(db.Model):
    id: Mapped[int] = mapped_column(Integer, primary_key=True, autoincrement=True)

    username: Mapped[str] = mapped_column(String(255), unique=True)
    password_hash: Mapped[str] = mapped_column(String(255))

    nickname: Mapped[str] = mapped_column(String(255))
    description: Mapped[str] = mapped_column(Text, default="")

    registered_at: Mapped[datetime] = mapped_column(DateTime)
    last_login_at: Mapped[datetime] = mapped_column(DateTime)

    token_validity_period: Mapped[int] = mapped_column(Integer, default=604800)

    posts = relationship("Post", back_populates="author")
    series = relationship("Series", back_populates="author")

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

    def to_dict(self) -> dict[str, Any]:
        return dict(
            id=self.id,
            username=self.username,
            nickname=self.nickname,
            description=self.description,
            registered_at=self.registered_at.isoformat(),
            last_login_at=self.last_login_at.isoformat(),
            token_validity_period=self.token_validity_period,
        )


class Tag(db.Model):
    id: Mapped[int] = mapped_column(Integer, primary_key=True, autoincrement=True)

    name: Mapped[str] = mapped_column(String(255))
    slug: Mapped[str] = mapped_column(String(255))

    posts: Mapped[list["Post"]] = relationship(
        secondary=post_tag_association, back_populates="tags"
    )


class Series(db.Model):
    id: Mapped[int] = mapped_column(Integer, primary_key=True, autoincrement=True)

    name: Mapped[str] = mapped_column(String(255))
    cover: Mapped[str] = mapped_column(String(255))

    author_id: Mapped[int] = mapped_column(Integer, ForeignKey("user.id"))
    author: Mapped["User"] = relationship("User", back_populates="series")

    posts: Mapped["list[Post]"] = relationship("Post", back_populates="series")


class Post(db.Model):
    id: Mapped[int] = mapped_column(Integer, primary_key=True, autoincrement=True)

    title: Mapped[str] = mapped_column(String(255))
    slug: Mapped[str] = mapped_column(String(255))

    body: Mapped[str] = mapped_column(Text)

    created_at: Mapped[datetime] = mapped_column(DateTime)
    updated_at: Mapped[datetime] = mapped_column(DateTime)

    is_published: Mapped[bool] = mapped_column(Boolean)
    published_at: Mapped[datetime] = mapped_column(DateTime)

    series_id: Mapped[int] = mapped_column(Integer, ForeignKey("series.id"))
    series = relationship("Series", back_populates="posts")

    author_id: Mapped[int] = mapped_column(Integer, ForeignKey("user.id"))
    author = relationship("User", back_populates="posts")

    tags: Mapped[list["Tag"]] = relationship(
        secondary=post_tag_association, back_populates="posts"
    )
