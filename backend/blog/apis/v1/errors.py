from flask import jsonify


def abort(
    target: str = "undefined",
    message: str = "error occured.",
    status_code: int = 400,
):
    return (
        jsonify(
            dict(
                error=dict(
                    target=target,
                    message=message,
                    status_code=status_code,
                )
            )
        ),
        status_code,
    )
