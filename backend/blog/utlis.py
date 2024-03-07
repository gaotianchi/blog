import re
from datetime import datetime, timezone
from typing import Any, cast

import markdown
from bs4 import BeautifulSoup
from jsonschema import validate
from jsonschema.exceptions import SchemaError, ValidationError

from .config import get_config

CONFIG = get_config()


def deserialize_datetime(date_string: str) -> datetime:
    given_time = datetime.strptime(date_string, "%Y-%m-%dT%H:%M:%S%z")
    utc_time = given_time.astimezone(timezone.utc)
    return utc_time


def serialize_datetime(date_object: datetime) -> str:
    return date_object.strftime("%Y-%m-%dT%H:%M:%S") + "+0000"


def validator(data: Any, schema: dict[str, Any]) -> str | None:
    try:
        validate(data, schema)
    except SchemaError as e:
        return e.message
    except ValidationError as e:
        return e.message
    else:
        return None


def allowed_image(filename: str):
    return (
        "." in filename
        and filename.rsplit(".", 1)[1].lower() in CONFIG.ALLOWED_EXTENSIONS
    )


def get_all_image_url(text: str) -> list[str]:
    pattern = re.compile(r"!\[.*?\]\((.*?)\)", re.MULTILINE)
    search_results = cast(list[str], re.findall(pattern, text))
    return search_results


def markdown_to_text(markdown_text: str):
    html_content = markdown.markdown(markdown_text)
    soup = BeautifulSoup(html_content, "html.parser")
    plain_text = soup.get_text(separator="\n")
    return plain_text.replace("\n\n", "\n")
