SCHEMA = "http://json-schema.org/draft-07/schema#"
DATETIMEPATTERN = "^(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})([+-]\\d{4})$"

schema_01 = {
    "$schema": SCHEMA,
    "type": "object",
    "properties": {
        "username": {"type": "string", "pattern": "^[a-z][a-z0-9_]{2,99}$"},
        "password": {
            "type": "string",
            "pattern": "^(?=.*[a-zA-Z])(?=.*\\d).{6,}$",
        },
    },
    "required": ["username", "password"],
    "additionalProperties": False,
}

schema_02 = {
    "$schema": SCHEMA,
    "type": "object",
    "properties": {
        "accessToken": {"type": "string"},
        "tokenType": {"type": "string", "enum": ["Bearer"]},
    },
    "required": ["accessToken", "tokenType"],
    "additionalProperties": False,
}

schema_03 = {
    "$schema": SCHEMA,
    "type": "object",
    "properties": {
        "description": {"type": "string"},
        "id": {"type": "integer"},
        "lastLoginAt": {"type": "string", "pattern": DATETIMEPATTERN},
        "nickname": {"type": "string"},
        "registeredAt": {"type": "string", "pattern": DATETIMEPATTERN},
        "tokenValidityPeriod": {"type": "integer"},
        "username": {"type": "string"},
    },
    "required": [
        "id",
        "lastLoginAt",
        "nickname",
        "registeredAt",
        "tokenValidityPeriod",
        "username",
    ],
}

schema_04 = {  # type: ignore
    "$schema": SCHEMA,
    "type": "object",
    "properties": {
        "id": {"type": "integer"},
        "authorId": {"type": "integer"},
        "body": {"type": "string"},
        "createdAt": {"type": "string", "pattern": DATETIMEPATTERN},
        "isPublished": {"type": "boolean"},
        "publishedAt": {"type": "string", "pattern": DATETIMEPATTERN},
        "seriesId": {"type": "integer"},
        "slug": {"type": "string"},
        "tags": {"type": "array", "items": {"type": "string"}},
        "title": {"type": "string"},
        "updatedAt": {"type": "string", "pattern": DATETIMEPATTERN},
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
        "seriesId",
    ],
}

schema_05 = {
    "$schema": SCHEMA,
    "type": "object",
    "properties": {
        "id": {"type": "integer"},
        "authorId": {"type": "integer"},
        "name": {"type": "string"},
        "cover": {"type": "string"},
        "createdAt": {"type": "string", "pattern": DATETIMEPATTERN},
    },
    "required": ["id", "authorId", "name", "cover", "createdAt"],
}

schema_06 = {
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


schema_07 = {
    "$schema": SCHEMA,
    "type": "array",
    "items": {
        "type": "object",
        "properties": {"id": {"type": "integer"}, "name": {"type": "string"}},
        "required": ["id", "name"],
    },
}

schema_08 = {
    "$schema": SCHEMA,
    "type": "array",
    "items": {
        "type": "object",
        "properties": {
            "id": {"type": "integer"},
            "authorId": {"type": "integer"},
            "name": {"type": "string"},
            "cover": {"type": "string"},
            "createdAt": {"type": "string", "pattern": DATETIMEPATTERN},
            "seriesId": {"type": "integer"},
        },
        "required": ["id", "authorId", "name", "cover", "createdAt", "seriesId"],
    },
}

schema_09 = {
    "$schema": SCHEMA,
    "type": "array",
    "items": {
        "type": "object",
        "properties": {
            "id": {"type": "integer"},
            "title": {"type": "string"},
            "createdAt": {"type": "string", "pattern": DATETIMEPATTERN},
            "isPublished": {"type": "boolean"},
            "tags": {"type": "array", "items": {"type": "string"}},
            "images": {"type": "array", "items": {"type": "string"}},
            "author": {"type": "string"},
            "planned": {"type": "boolean"},
        },
        "required": [
            "id",
            "title",
            "createdAt",
            "isPublished",
            "tags",
            "images",
            "author",
            "planned",
        ],
    },
}

schema_10 = {  # type: ignore
    "$schema": SCHEMA,
    "type": "object",
    "properties": {
        "isPublished": {"type": "boolean"},
        "tags": {"type": "array", "items": {"type": "string"}},
    },
    "required": [],
}

schema_11 = {
    "$schema": SCHEMA,
    "type": "object",
    "properties": {
        "id": {"type": "integer"},
        "title": {"type": "string"},
        "createdAt": {"type": "string", "pattern": DATETIMEPATTERN},
        "isPublished": {"type": "boolean"},
        "tags": {"type": "array", "items": {"type": "string"}},
        "images": {"type": "array", "items": {"type": "string"}},
        "author": {"type": "string"},
        "planned": {"type": "boolean"},
        "seriesId": {"type": "integer"},
    },
    "required": [
        "id",
        "title",
        "createdAt",
        "isPublished",
        "tags",
        "images",
        "author",
        "planned",
        "seriesId",
    ],
}

schema_12 = {
    "$schema": SCHEMA,
    "type": "array",
    "items": {
        "type": "object",
        "properties": {
            "id": {"type": "integer"},
            "author": {"type": "string"},
            "name": {"type": "string"},
            "cover": {"type": "string"},
            "createdAt": {"type": "string", "pattern": DATETIMEPATTERN},
        },
        "required": ["id", "author", "name", "cover", "createdAt"],
    },
}
schema_13 = {
    "$schema": SCHEMA,
    "type": "object",
    "properties": {
        "id": {"type": "integer"},
        "author": {"type": "string"},
        "name": {"type": "string"},
        "cover": {"type": "string"},
        "createdAt": {"type": "string", "pattern": DATETIMEPATTERN},
    },
    "required": ["id", "author", "name", "cover", "createdAt"],
}
schema_14 = {
    "$schema": SCHEMA,
    "type": "array",
    "items": {
        "type": "object",
        "properties": {
            "seriesId": {"type": "integer"},
            "articlesCount": {"type": "integer"},
        },
        "required": ["seriesId", "articlesCount"],
    },
}
