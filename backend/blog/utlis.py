from typing import Any

from jsonschema import validate
from jsonschema.exceptions import SchemaError, ValidationError

from .config import get_config

CONFIG = get_config()


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
