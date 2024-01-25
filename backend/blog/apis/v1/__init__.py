from flask import Blueprint

from .account import account
from .author import author
from .vistor import vistor

v1 = Blueprint("v1", __name__)

v1.register_blueprint(account)
v1.register_blueprint(author)
v1.register_blueprint(vistor)
