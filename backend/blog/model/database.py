from datetime import datetime

from sqlalchemy import Boolean, DateTime, ForeignKey, Integer, String, Text
from sqlalchemy.orm import Mapped, mapped_column, relationship
from werkzeug.security import generate_password_hash

from blog.extens import db


class User(db.Model):
    id: Mapped[int] = mapped_column(Integer, primary_key=True, autoincrement=True)

    username: Mapped[str] = mapped_column(String(255), unique=True)
    password_hash: Mapped[str] = mapped_column(String(255))

    nickname: Mapped[str] = mapped_column(String(255))
    description: Mapped[str] = mapped_column(Text, nullable=True)

    registered_at: Mapped[datetime] = mapped_column(DateTime)
    last_login_at: Mapped[datetime] = mapped_column(DateTime)

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


class Category(db.Model):
    id: Mapped[int] = mapped_column(Integer, primary_key=True, autoincrement=True)

    name: Mapped[str] = mapped_column(String(255))
    slug: Mapped[str] = mapped_column(String(255))

    posts = relationship("Post", back_populates="category")


class Series(db.Model):
    id: Mapped[int] = mapped_column(Integer, primary_key=True, autoincrement=True)

    name: Mapped[str] = mapped_column(String(255))
    slug: Mapped[str] = mapped_column(String(255))

    author_id: Mapped[int] = mapped_column(Integer, ForeignKey("user.id"))
    author = relationship("User", back_populates="series")

    posts = relationship("Post", back_populates="series")


class Post(db.Model):
    id: Mapped[int] = mapped_column(Integer, primary_key=True, autoincrement=True)

    title: Mapped[str] = mapped_column(String(255))
    slug: Mapped[str] = mapped_column(String(255))

    body: Mapped[str] = mapped_column(Text)

    created_at: Mapped[datetime] = mapped_column(DateTime)
    updated_at: Mapped[datetime] = mapped_column(DateTime)

    is_published: Mapped[bool] = mapped_column(Boolean)
    published_at: Mapped[datetime] = mapped_column(DateTime)

    category_id: Mapped[int] = mapped_column(Integer, ForeignKey("category.id"))
    category = relationship("Category", back_populates="posts")

    series_id: Mapped[int] = mapped_column(Integer, ForeignKey("series.id"))
    series = relationship("Series", back_populates="posts")

    author_id: Mapped[int] = mapped_column(Integer, ForeignKey("user.id"))
    author = relationship("User", back_populates="posts")
