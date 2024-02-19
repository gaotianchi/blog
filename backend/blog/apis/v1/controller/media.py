from pathlib import Path
from typing import cast

from flask import Blueprint, current_app, jsonify, request, send_file, url_for
from werkzeug.utils import secure_filename

from blog.apis.v1.controller.account import auth_required
from blog.apis.v1.errors import abort
from blog.utlis import allowed_image

media = Blueprint("media", __name__, url_prefix="/media")


@media.route("/uploads", methods=["POST"])
@auth_required
def new_upload():
    file = request.files.get("file")
    if not file:
        return abort(message="No file found.")
    if not file.filename:
        return abort(message="Invalid filename.")
    if not allowed_image(file.filename):
        return abort(message="Invalid file extension.")
    if secure_filename(file.filename) != file.filename:
        return abort(message="Invalid filename.")
    filename = secure_filename(file.filename)
    file.save(cast(Path, current_app.config["UPLOAD_FOLDER"]).joinpath(filename))  # type: ignore
    response_data = dict(
        url=url_for("v1.media.download", filename=filename, _external=True)
    )
    return jsonify(response_data), 201


@media.route("/uploads/<filename>", methods=["GET"])
def download(filename: str):
    filepath = cast(Path, current_app.config["UPLOAD_FOLDER"]).joinpath(filename)
    if not filepath.exists():
        return abort(message="No file found.")
    return send_file(filepath)
