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
