import click
from flask import Flask

from blog.extens import db


def register_commands(app: Flask) -> None:
    @app.cli.command("initdb", help="Initialize the database.")
    def initdb():  # type: ignore
        click.confirm(
            "The next operation will delete all data. Whether to continue?",
            abort=True,
        )
        db.drop_all()
        click.echo("Successfully delete all data.")

        db.create_all()
        click.echo("Successfully create all tables.")
