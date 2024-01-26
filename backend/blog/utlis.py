from typing import Any

from jsonschema import validate
from jsonschema.exceptions import SchemaError, ValidationError


def validator(data: Any, schema: dict[str, Any]) -> str | None:
    try:
        validate(data, schema)
    except SchemaError as e:
        return e.message
    except ValidationError as e:
        return e.message
    else:
        return None
