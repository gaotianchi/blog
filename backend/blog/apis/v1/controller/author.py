from typing import Any, cast

from flask import Blueprint, g, jsonify, request

from blog.apis.v1.errors import abort
from blog.apis.v1.schemas import SCHEMA_04  # type: ignore
from blog.model.database import Article, User
from blog.utlis import validator

from .account import auth_required

author = Blueprint("author", __name__, url_prefix="/author")


@author.route("/articles", methods=["POST"])
@auth_required
def new_article():
    current_user = cast(User, g.current_user)
    new_article = Article.create(author=current_user)

    return jsonify(f"Successfully create article {new_article}."), 201


@author.route("/article/<int:id>", methods=["PATCH"])
@auth_required
def update(id: int):
    current_article = cast(Article, Article.query.get(id))
    if not current_article:
        return abort(message="No article found.")

    data = cast(dict[str, Any], request.json)
    request_data = dict(
        title=data.get("title"),
        body=data.get("body"),
        slug=data.get("slug"),
        is_published=data.get("is_published"),
        series=data.get("series"),
        tags=data.get("tags"),
    )
    if validator(request_data, SCHEMA_04):  # type: ignore
        return abort()

    new_article = current_article.update(**request_data)  # type: ignore

    return jsonify(new_article.to_dict()), 200  # type: ignore
