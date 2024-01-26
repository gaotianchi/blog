from flask import Blueprint, jsonify, request

from blog.model.database import User
from blog.utlis import validator

from ..errors import abort
from ..schemas import schema_01

account = Blueprint("account", __name__, url_prefix="/account")


@account.route("/users", methods=["POST"])
def new_user():
    data = dict(
        username=request.form.get("username"),
        password=request.form.get("password"),
    )
    if validator(data, schema_01):
        return abort()

    if User.query.filter_by(username=data["username"]).first():
        return abort("username", f"username <{data['username']}> has been used.")

    User.create(data["username"], data["password"])  # type: ignore

    return jsonify(f"Created user <{data['username']}>."), 201
