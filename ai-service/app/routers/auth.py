from fastapi import APIRouter
from fastapi.responses import RedirectResponse

from google_auth_oauthlib.flow import InstalledAppFlow
from googleapiclient.discovery import build

from service.google_auth_service import GoogleAuthService

auth_router = APIRouter(prefix="/auth", tags=["auth"])

SCOPES = ['https://googleapis.com/auth/calendar']
CREDENTIALS_FILE = 'credentials.json'
google_auth_service = GoogleAuthService()

@auth_router.get("/auth/google")
async def auth_google():
    
    return RedirectResponse(url=google_auth_service.get_authorization_url(), status_code=304)

@auth_router.get("/auth/google/callback")
async def auth_google_callback(code: str):
    token=google_auth_service.exchange_code_for_token(code)
    return {"Status": "OK", "Token": token}



