schema_01 = {
    "$schema": "http://json-schema.org/draft-07/schema#",
    "type": "object",
    "properties": {
        "username": {"type": "string", "pattern": "^[a-z][a-z0-9_]{2,49}$"},
        "password": {
            "type": "string",
            "pattern": "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{7,}$",
        },
    },
    "required": ["username", "password"],
}

schema_02 = {
    "$schema": "http://json-schema.org/draft-07/schema#",
    "type": "object",
    "properties": {
        "access_token": {"type": "string"},
        "token_type": {"type": "string", "enum": ["Bearer"]},
    },
    "required": ["access_token", "token_type"],
}

schema_03 = {
    "$schema": "http://json-schema.org/draft-07/schema#",
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

schema_04 = {  # type: ignore
    "$schema": "http://json-schema.org/draft-07/schema#",
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

schema_05 = {
    "$schema": "http://json-schema.org/draft-07/schema#",
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

schema_06 = {
    "$schema": "http://json-schema.org/draft-07/schema#",
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


schema_07 = {
    "$schema": "http://json-schema.org/draft-07/schema#",
    "type": "array",
    "items": {
        "type": "object",
        "properties": {"id": {"type": "integer"}, "name": {"type": "string"}},
        "required": ["id", "name"],
    },
}

schema_08 = {
    "$schema": "http://json-schema.org/draft-07/schema#",
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
