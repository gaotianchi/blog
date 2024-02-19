from datetime import datetime, timezone


def deserialize_datetime(date_string: str) -> datetime:
    given_time = datetime.strptime(date_string, "%Y-%m-%dT%H:%M:%S%z")
    utc_time = given_time.astimezone(timezone.utc)
    return utc_time


def serialize_datetime(date_object: datetime) -> str:
    return date_object.strftime("%Y-%m-%dT%H:%M:%S%z")
