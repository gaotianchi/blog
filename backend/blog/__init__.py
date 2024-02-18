from flask import Flask

from .apis import v1_bp
from .commands import register_commands
from .config import get_config
from .extens import cors, db
from .model.database import Article


def create_app(environment: str | None = None) -> Flask:
    config = get_config(environment)
    app = Flask(__package__)
    app.config.from_object(config)
    db.init_app(app)
    cors.init_app(app)
    app.register_blueprint(v1_bp)
    register_commands(app)

    @app.shell_context_processor
    def make_shell_context():  # type: ignore
        return dict(db=db, a=Article)

    return app
