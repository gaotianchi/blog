SCHEMA = "http://json-schema.org/draft-07/schema#"
USERNAME = {"type": "string", "pattern": "^[a-z][a-z0-9_]{2,49}$"}
PASSWORD = {
    "type": "string",
    "pattern": "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{7,}$",
}


SCHEMA_01 = {
    "$schema": SCHEMA,
    "type": "object",
    "properties": {
        "username": USERNAME,
        "password": PASSWORD,
    },
    "required": ["username", "password"],
}

SCHEMA_02 = {
    "$schema": SCHEMA,
    "type": "object",
    "properties": {
        "access_token": {"type": "string"},
        "token_type": {"type": "string", "enum": ["Bearer"]},
    },
    "required": ["access_token", "token_type"],
}

SCHEMA_03 = {
    "$schema": SCHEMA,
    "type": "object",
    "properties": {
        "description": {"type": "string"},
        "id": {"type": "integer"},
        "last_login_at": {"type": "string", "format": "date-time"},
        "nickname": {"type": "string"},
        "registered_at": {"type": "string", "format": "date-time"},
        "token_validity_period": {"type": "integer"},
        "username": {"type": "string"},
    },
    "required": [
        "id",
        "last_login_at",
        "nickname",
        "registered_at",
        "token_validity_period",
        "username",
    ],
}

SCHEMA_04 = {  # type: ignore
    "$schema": SCHEMA,
    "type": "object",
    "properties": {
        "id": {"type": "integer"},
        "author_id": {"type": "integer"},
        "body": {"type": "string"},
        "created_at": {"type": "string", "format": "date-time"},
        "is_published": {"type": "boolean"},
        "published_at": {"type": "string", "format": "date-time"},
        "series_id": {"type": ["integer", "null"]},
        "slug": {"type": "string"},
        "tags": {"type": "array", "items": {"type": "string"}},
        "title": {"type": "string"},
        "updated_at": {"type": "string", "format": "date-time"},
    },
    "required": [
        "id",
        "body",
        "created_at",
        "is_published",
        "published_at",
        "slug",
        "tags",
        "title",
        "updated_at",
    ],
}

SCHEMA_05 = {
    "$schema": SCHEMA,
    "type": "object",
    "properties": {
        "id": {"type": "integer"},
        "author_id": {"type": "integer"},
        "name": {"type": "string"},
        "cover": {"type": ["string", "null"]},
    },
    "required": [
        "id",
        "author_id",
        "name",
        "cover",
    ],
}
