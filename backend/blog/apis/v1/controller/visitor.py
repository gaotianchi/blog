from typing import cast

from flask import Blueprint, jsonify

from blog.apis.v1.errors import abort
from blog.apis.v1.schemas import (  # type: ignore; schema_05,; schema_06,; schema_07,; schema_08,; schema_09,; schema_11,; schema_14,
    schema_04,
    schema_15,
)
from blog.model.database import Article, User
from blog.utlis import (
    get_all_image_url,
    markdown_to_text,
    serialize_datetime,
    validator,
)

visitor = Blueprint("vistor", __name__, url_prefix="/visitor")


def get_article_preview_card(a: Article) -> dict[str, int | str | list[str]]:
    card: dict[str, int | str | list[str]] = {}
    card["id"] = a.id
    card["title"] = a.title
    card["createdAt"] = serialize_datetime(a.created_at)
    card["tags"] = [tag.name for tag in a.tags]
    card["images"] = get_all_image_url(a.body)
    card["author"] = cast(User, a.author).nickname
    card["summary"] = markdown_to_text(a.body)[0:500]
    card["slug"] = a.slug
    return card


@visitor.route("/article-preview-cards/<int:page>")
def get_article_preview_cards(page: int):
    articles = cast(list[Article], Article.query.paginate(page=page, per_page=10).items)
    response_data = [get_article_preview_card(a) for a in articles]
    if validator(response_data, schema_15):
        return abort()
    return jsonify(response_data), 200


@visitor.route("/article/<int:id>")
def get_article_item(id: int):
    article = cast(Article | None, Article.query.get(id))
    if not article:
        return abort(message="No article found.", status_code=404)
    response_data = article.to_dict()
    if validator(response_data, schema_04):
        return abort()
    return jsonify(response_data), 200


@visitor.route("/article/<article_slug>")
def get_article_item_by_slug(article_slug: str):
    article = cast(Article | None, Article.query.filter_by(slug=article_slug).first())
    if not article:
        return abort(message="No article found.", status_code=404)
    response_data = article.to_dict()
    if validator(response_data, schema_04):
        return abort()
    return jsonify(response_data), 200
