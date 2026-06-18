from pydantic_settings import BaseSettings

class Settings(BaseSettings):
    gemini_api_key: str

    class Config:
        enf_file = ".env"

settings = Settings()