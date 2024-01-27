SCHEMA = "http://json-schema.org/draft-07/schema#"
USERNAME = {"type": "string", "pattern": "^[a-z][a-z0-9_]{2,49}$"}
PASSWORD = {
    "type": "string",
    "pattern": "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{7,}$",
}


schema_01 = {
    "$schema": SCHEMA,
    "type": "object",
    "properties": {
        "username": USERNAME,
        "password": PASSWORD,
    },
    "required": ["username", "password"],
}

schema_02 = {
    "$schema": SCHEMA,
    "type": "object",
    "properties": {
        "access_token": {"type": "string"},
        "token_type": {"type": "string", "enum": ["Bearer"]},
    },
    "required": ["access_token", "token_type"],
}

schema_03 = {
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
