package internship.api;

import internship.config.Config;
import internship.dto.AuthRequest;
import internship.dto.AuthResponse;
import internship.utils.RestHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.*;

public class AuthApiClient {

    private Response lastResponse;
    private AuthResponse lastAuthResponse;

    @Step("POST /auth с логином: {username}")
    public AuthApiClient authenticate(String username, String password) {
        AuthRequest request = new AuthRequest(username, password);
        lastResponse = RestHelper.post(Config.INSTANCE.herokuappAuthUrl(), request);
        lastAuthResponse = lastResponse.as(AuthResponse.class);
        return this;
    }

    @Step("Проверить статус-код: {expectedStatusCode}")
    public AuthApiClient verifyStatusCode(int expectedStatusCode) {
        assertEquals(expectedStatusCode, lastResponse.statusCode());
        return this;
    }

    @Step("Проверить наличие токена")
    public AuthApiClient verifyTokenPresent() {
        assertNotNull(lastAuthResponse.token());
        assertFalse(lastAuthResponse.token().isBlank());
        return this;
    }

    @Step("Проверить сообщение об ошибке: {expectedMessage}")
    public AuthApiClient verifyErrorMessage(String expectedMessage) {
        assertEquals(expectedMessage, lastAuthResponse.reason());
        return this;
    }
}