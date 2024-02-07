from typing import Any

from flask import Blueprint, jsonify

from .controller.account import account
from .controller.author import author
from .controller.media import media
from .controller.vistor import vistor

v1 = Blueprint("v1", __name__, url_prefix="/v1")

v1.register_blueprint(account)
v1.register_blueprint(author)
v1.register_blueprint(vistor)
v1.register_blueprint(media)


@v1.errorhandler(Exception)
def handle_error(error: Any):
    target = getattr(error, "target", "undefined")
    message = getattr(error, "description", "error occurred.")
    status_code = getattr(error, "status", 500)

    response = jsonify(
        {
            "error": {
                "target": target,
                "message": message,
                "status_code": status_code,
            }
        }
    )

    return response, status_code
