from datetime import datetime
from typing import Any, cast

from flask import Blueprint, g, jsonify, request

from blog.apis.v1.errors import abort
from blog.apis.v1.schemas import schema_10  # type: ignore
from blog.apis.v1.schemas import (  # type: ignore
    schema_04,
    schema_05,
    schema_06,
    schema_07,
    schema_08,
    schema_09,
    schema_11,
)
from blog.model.database import Article, Series, Tag, User
from blog.utlis import get_all_image_url, serialize_datetime, validator

from .account import auth_required

author = Blueprint("author", __name__, url_prefix="/author")


@author.route("/articles", methods=["POST"])
@auth_required
def new_article():
    current_user = cast(User, g.current_user)
    new_article = Article.create(author=current_user)
    response_data = new_article.to_dict()
    if validator(response_data, schema_04):
        return abort()
    return jsonify(response_data), 201


@author.route("/series", methods=["POST"])
@auth_required
def new_series():
    data = cast(dict[str, Any], request.json)
    if validator(data, schema_06):
        return abort()
    current_user = cast(User, g.current_user)
    new_series = Series.create(
        author=current_user, name=data["name"], cover=data["cover"]
    )
    response_data = new_series.to_dict()
    if validator(response_data, schema_05):
        return abort()
    return jsonify(response_data), 201


@author.route("/series/<int:id>", methods=["PATCH"])
@auth_required
def update_series(id: int):
    current_series = cast(Series, Series.query.get(id))
    if not current_series:
        return abort(message="No series found", status_code=404)
    data = cast(dict[str, Any], request.json)
    if validator(data, schema_05):
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
    if validator(data, schema_04):  # type: ignore
        return abort()
    current_user = cast(User, g.current_user)
    data_to_update = {}
    data_to_update["title"] = data["title"]
    data_to_update["body"] = data["body"]
    data_to_update["slug"] = data["slug"]
    data_to_update["is_published"] = data["isPublished"]
    data_to_update["published_at"] = datetime.strptime(
        data["publishedAt"], "%Y-%m-%dT%H:%M:%S%z"
    )
    if data["seriesId"]:
        series = Series.query.get(data["seriesId"])
        data_to_update["series"] = series
    tags: list[str] = data["tags"]
    data_to_update["tags"] = Tag.create(current_user, *tags)
    new_article = current_article.update(**data_to_update)  # type: ignore
    response_data = new_article.to_dict()
    if validator(response_data, schema_04):  # type: ignore
        return abort()

    return jsonify(response_data), 200  # type: ignore


@author.route("/article/<int:id>", methods=["GET"])
def get_article_data(id: int):
    current_article = cast(Article, Article.query.get(id))
    if not current_article:
        return abort(message="No article found.", status_code=404)
    response_data = current_article.to_dict()  # type: ignore
    if validator(response_data, schema_04):  # type: ignore
        return abort()
    return jsonify(response_data), 200


@author.route("/series/<int:id>", methods=["GET"])
def get_series_data(id: int):
    current_series = cast(Series, Series.query.get(id))
    if not current_series:
        return abort(message="No series fount", status_code=404)
    data = current_series.to_dict()
    if validator(data, schema_05):
        return abort()
    return jsonify(data), 200


@author.route("/tags", methods=["GET"])
@auth_required
def get_all_tags():
    current_user = cast(User, g.current_user)
    tags = cast(list[Tag], Tag.query.with_parent(current_user).all())
    tagsData = [dict(**tag.to_dict()) for tag in tags]  # type: ignore
    if validator(tagsData, schema_07):
        return abort()
    return jsonify(tagsData), 200


@author.route("/series", methods=["GET"])
@auth_required
def get_all_series():
    current_user = cast(User, g.current_user)
    series = cast(list[Series], Series.query.with_parent(current_user).all())
    seriesData = [dict(**s.to_dict()) for s in series]  # type: ignore
    if validator(seriesData, schema_08):
        return abort()
    return jsonify(seriesData), 200


def get_article_card(a: Article) -> dict[str, str | bool | list[str] | int]:
    card_data: dict[str, str | bool | list[str] | int] = {}
    card_data["id"] = a.id
    card_data["title"] = a.title
    card_data["isPublished"] = a.is_published
    card_data["createdAt"] = serialize_datetime(a.created_at)
    card_data["tags"] = [tag.name for tag in a.tags]
    card_data["images"] = get_all_image_url(a.body)
    card_data["author"] = cast(User, a.author).nickname
    card_data["planned"] = a.is_published and (a.published_at > datetime.now())
    return card_data


@author.route("/articles", methods=["GET"])
@auth_required
def get_all_articles():
    current_user = cast(User, g.current_user)
    articles = cast(list[Article], Article.query.with_parent(current_user).all())
    response_data: list[dict[str, str | bool | list[str] | int]] = []
    for a in articles:
        response_data.append(get_article_card(a))
    if validator(response_data, schema_09):
        return abort()
    return jsonify(response_data), 200


@author.route("/article-card/<int:id>", methods=["PATCH"])
@auth_required
def update_article_card(id: int):
    current_article = cast(Article, Article.query.get(id))
    if not current_article:
        return abort(message="No article found.")
    data = cast(dict[str, Any], request.json)
    if validator(data, schema_10):  # type: ignore
        return abort()
    current_user = cast(User, g.current_user)
    data_to_update = {}
    if data.get("isPublished") is not None:
        data_to_update["is_published"] = data["isPublished"]
    if data.get("tags") is not None:
        tags: list[str] = data["tags"]
        data_to_update["tags"] = Tag.create(current_user, *tags)
    new_article = current_article.update_card(**data_to_update)  # type: ignore
    response_data = get_article_card(new_article)
    if validator(response_data, schema_11):  # type: ignore
        return abort()
    return jsonify(response_data), 200  # type: ignore


@author.route("/article/<int:id>", methods=["DELETE"])
@auth_required
def delete_ariticle(id: int):
    current_article = cast(Article, Article.query.get(id))
    if not current_article:
        return abort(message="No article found.")
    current_article.delete()
    return jsonify("Deleted"), 204
