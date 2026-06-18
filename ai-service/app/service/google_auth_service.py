from google_auth_oauthlib.flow import Flow



class GoogleAuthService:
    def __init__(self):
        self.flow = Flow.from_client_secrets_file(client_secrets_file='ai-service/app/credentials.json',
                                                  scopes='https://www.googleapis.com/auth/calendar',
                                                  redirect_uri='http://127.0.0.1:8000'
                                                  )

    def auth_google(self):
        authorization_url, state = self.flow.authorization_url()
        return authorization_url

    def exchange_code_for_token(self, code: str):
        token=self.flow.fetch_token(code=code)
        print(token)
        return token