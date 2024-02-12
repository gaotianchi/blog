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
        "authorId": {"type": "integer"},
        "body": {"type": "string"},
        "createdAt": {"type": "string", "format": "date-time"},
        "isPublished": {"type": "boolean"},
        "publishedAt": {"type": "string", "format": "date-time"},
        "seriesId": {"type": ["integer", "null"]},
        "slug": {"type": "string"},
        "tags": {"type": "array", "items": {"type": "string"}},
        "title": {"type": "string"},
        "updatedAt": {"type": "string", "format": "date-time"},
    },
    "required": [
        "id",
        "body",
        "createdAt",
        "isPublished",
        "publishedAt",
        "slug",
        "tags",
        "title",
        "updatedAt",
    ],
}

SCHEMA_05 = {
    "$schema": SCHEMA,
    "type": "object",
    "properties": {
        "id": {"type": "integer"},
        "author_id": {"type": "integer"},
        "name": {"type": "string"},
        "cover": {"type": "string"},
    },
    "required": [
        "id",
        "author_id",
        "name",
        "cover",
    ],
}

SCHEMA_06 = {
    "$schema": SCHEMA,
    "type": "object",
    "properties": {
        "name": {"type": "string"},
        "cover": {"type": "string"},
    },
    "required": [
        "name",
        "cover",
    ],
}


SCHEMA_07 = {
    "$schema": SCHEMA,
    "type": "array",
    "items": {
        "type": "object",
        "properties": {"id": {"type": "integer"}, "name": {"type": "string"}},
        "required": ["id", "name"],
    },
}

SCHEMA_08 = {
    "$schema": SCHEMA,
    "type": "array",
    "items": {
        "type": "object",
        "properties": {
            "id": {"type": "integer"},
            "author_id": {"type": "integer"},
            "name": {"type": "string"},
            "cover": {"type": "string"},
        },
        "required": [
            "id",
            "author_id",
            "name",
            "cover",
        ],
    },
}
