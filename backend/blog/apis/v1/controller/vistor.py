from flask import Blueprint

vistor = Blueprint("vistor", __name__)


@vistor.route("/hello")
def hello():
    return "Hello!"
