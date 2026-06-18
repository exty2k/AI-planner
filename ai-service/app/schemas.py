from pydantic import BaseModel


class RequestSchema(BaseModel):
    user_id: int
    text: str