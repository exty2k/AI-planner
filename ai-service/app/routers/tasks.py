from fastapi import APIRouter

from schemas import RequestSchema

from config import gemini_api_key

tasks_router = APIRouter(prefix="/tasks", tags=["tasks"])


@tasks_router.post("/task")
async def user_request(request: RequestSchema):
    text = request.text
        
    return 
