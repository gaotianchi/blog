import time
from functools import wraps
from typing import Any, Callable, Tuple, Union, cast

from cryptography.fernet import Fernet, InvalidToken
from flask import Blueprint, current_app, g, jsonify, request

from blog.model.database import User
from blog.utlis import validator

from ..errors import abort
from ..schemas import SCHEMA_01, SCHEMA_02, SCHEMA_03

account = Blueprint("account", __name__, url_prefix="/account")


@account.route("/users", methods=["POST"])
def new_user():
    data = dict(
        username=request.form.get("username"),
        password=request.form.get("password"),
    )
    if validator(data, SCHEMA_01):
        return abort()

    if User.query.filter_by(username=data["username"]).first():
        return abort("username", f"username <{data['username']}> has been used.")

    user = User.create(cast(str, data["username"]), cast(str, data["password"]))
    if validator(user.to_dict(), SCHEMA_03):
        return abort()

    return jsonify(f"Created user <{data['username']}>."), 201


@account.route("/token", methods=["POST"])
def new_token():
    grant_type = request.form.get("grant_type")
    if grant_type != "password":
        return abort("grant_type", "Invalid grant type.")

    request_data = dict(
        username=request.form.get("username"),
        password=request.form.get("password"),
    )

    if validator(request_data, SCHEMA_01):
        return abort()

    user = User.query.filter_by(username=request_data["username"]).first()  # type: ignore
    if not user:
        return abort(
            "username", f"No user named <{request_data['username']}> found.", 404
        )

    if not cast(User, user).validate_password(cast(str, request_data["password"])):
        return abort("password", "Invalid password.", 401)

    access_token = generate_access_token(cast(User, user))
    token_type = "Bearer"

    response_data = dict(access_token=access_token, token_type=token_type)
    if validator(response_data, SCHEMA_02):
        return abort()

    return jsonify(response_data), 200


def generate_access_token(user: User) -> str:
    f = Fernet(cast(bytes, current_app.config["SECRET_KEY"]))
    data = bytes(f"{user.username}", encoding="utf-8")
    return f.encrypt(data).decode("utf-8")


def get_access_token():
    if "Authorization" in request.headers:
        try:
            token_type, access_token = request.headers["Authorization"].split(None, 1)
        except ValueError:
            token_type = access_token = None
    else:
        token_type = access_token = None

    return token_type, access_token


def validate_access_token(token: str) -> bool:
    f = Fernet(cast(bytes, current_app.config["SECRET_KEY"]))
    try:
        username = f.decrypt(token.encode("utf-8"))
        if not username:
            return False
        user = User.query.filter_by(username=username.decode("utf-8")).first()  # type: ignore
        if not user:
            return False
        else:
            f.decrypt_at_time(
                token.encode("utf-8"),
                cast(User, user).token_validity_period,
                int(time.time()),
            )
    except InvalidToken:
        return False
    except Exception:
        return False
    else:
        g.current_user = user
        return True


def auth_required(f: Callable[..., Any]) -> Callable[..., Any]:
    @wraps(f)
    def decorated(
        *args: Any, **kwargs: Any
    ) -> Union[Callable[..., Any], Tuple[Any, int]]:
        token_type, access_token = get_access_token()

        if request.method != "OPTIONS":
            if token_type is None or token_type.lower() != "bearer":
                return abort("token_type", "The token type must be bearer.")
            if access_token is None:
                return abort("access_token", "No access token found.")
            if not validate_access_token(access_token):
                return abort("access_token", "Invalid token.", 401)
        return f(*args, **kwargs)

    return decorated
