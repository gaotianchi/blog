from datetime import datetime
from typing import Any, cast

from flask import Blueprint, g, jsonify, request

from blog.apis.v1.errors import abort
from blog.apis.v1.schemas import SCHEMA_04, SCHEMA_05, SCHEMA_06  # type: ignore
from blog.model.database import Article, Series, Tag, User
from blog.utlis import validator

from .account import auth_required

author = Blueprint("author", __name__, url_prefix="/author")


@author.route("/articles", methods=["POST"])
@auth_required
def new_article():
    current_user = cast(User, g.current_user)
    new_article = Article.create(author=current_user)
    return jsonify(f"Successfully create article {new_article}."), 201


@author.route("/series", methods=["POST"])
@auth_required
def new_series():
    data = cast(dict[str, Any], request.json)
    if validator(data, SCHEMA_06):
        return abort()
    current_user = cast(User, g.current_user)
    new_series = Series.create(
        author=current_user, name=data["name"], cover=data["cover"]
    )
    responseData = new_series.to_dict()
    if validator(responseData, SCHEMA_05):
        return abort()
    return jsonify(responseData), 201


@author.route("/series/<int:id>", methods=["PATCH"])
@auth_required
def update_series(id: int):
    current_series = cast(Series, Series.query.get(id))
    if not current_series:
        return abort(message="No series found", status_code=404)
    data = cast(dict[str, Any], request.json)
    if validator(data, SCHEMA_05):
        return abort()
    data_to_update = {}
    data_to_update["name"] = data["name"]
    data_to_update["cover"] = data["cover"]
    new_series = Series.update(**data_to_update)  # type: ignore
    return jsonify(new_series.to_dict()), 200


@author.route("/article/<int:id>", methods=["PATCH"])
@auth_required
def update_article(id: int):
    current_article = cast(Article, Article.query.get(id))
    if not current_article:
        return abort(message="No article found.")
    data = cast(dict[str, Any], request.json)
    if validator(data, SCHEMA_04):  # type: ignore
        return abort()
    data_to_update = {}
    data_to_update["title"] = data["title"]
    data_to_update["body"] = data["body"]
    data_to_update["slug"] = data["slug"]
    data_to_update["is_published"] = data["is_published"]
    data_to_update["published_at"] = datetime.strptime(
        data["published_at"], "%Y-%m-%dT%H:%M:%S.%fZ"
    )
    if data["series_id"]:
        series = Series.query.get(data["series_id"])
        data_to_update["series"] = series
    tags: list[str] = data["tags"]
    data_to_update["tags"] = Tag.create(*tags)
    new_article = current_article.update(**data_to_update)  # type: ignore
    responseData = new_article.to_dict()
    if validator(responseData, SCHEMA_04):  # type: ignore
        return abort()

    return jsonify(responseData), 200  # type: ignore


@author.route("/article/<int:id>", methods=["GET"])
def get_article_data(id: int):
    current_article = cast(Article, Article.query.get(id))
    if not current_article:
        return abort(message="No article found.", status_code=404)
    data = current_article.to_dict()  # type: ignore
    if validator(data, SCHEMA_04):  # type: ignore
        return abort()
    return jsonify(data), 200


@author.route("/series/<int:id>")
def get_series_data(id: int):
    current_series = cast(Series, Series.query.get(id))
    if not current_series:
        return abort(message="No series fount", status_code=404)
    data = current_series.to_dict()
    if validator(data, SCHEMA_05):
        return abort()
    return jsonify(data), 200
