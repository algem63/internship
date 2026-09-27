package internship.tests.week1;

import internship.dto.AuthRequest;
import internship.utils.RestHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTest {

    private static final String AUTH_URI = "https://restful-booker.herokuapp.com/auth";
    private static final String BAD_CREDENTIALS = "Bad credentials";

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("internship.utils.TestDataProvider#validCredentialsProvider")
    @DisplayName("Валидные логин и пароль")
    void validCredentials(String testName, String username, String password) {
        Response response = RestHelper.post(AUTH_URI, new AuthRequest(username, password));
        assertEquals(200, response.statusCode(),
                "Неверный статус-код для: " + testName);
        assertTrue(response.jsonPath().getMap("$").containsKey("token"),
                "Отсутствует token для: " + testName);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("internship.utils.TestDataProvider#invalidCredentialsProvider")
    void invalidCredentials(String testName, Object username, Object password, int expectedStatus) {
        Response response = RestHelper.post(AUTH_URI, new AuthRequest(username, password));
        assertEquals(expectedStatus, response.statusCode());
        assertEquals(BAD_CREDENTIALS, response.jsonPath().getString("reason"));
    }

    @Test
    @DisplayName("Отсутствует поле 'username' и валидный пароль")
    public void noUsernameAndValidPassword() {
        String request = """
                {
                    "password" : "password123"
                }
                """;
        Response response = RestHelper.post(
                AUTH_URI,
                request);
        assertEquals(BAD_CREDENTIALS, response.jsonPath().getString("reason"));
        assertEquals(400, response.statusCode());
    }

    @Test
    @DisplayName("Валидный username и отсутствие поля 'password'")
    public void validLoginAndNoPassword() {
        String request = """
                {
                    "username" : "admin"
                }
                """;
        Response response = RestHelper.post(
                AUTH_URI,
                request);
        assertEquals(400, response.statusCode());
        assertEquals(BAD_CREDENTIALS, response.jsonPath().getString("reason"));
    }

    @Test
    @DisplayName("Отсутствует тело запроса")
    public void noRequestBody() {
        Response response = RestHelper.postWithEmptyBody(
                AUTH_URI);
        assertEquals(400, response.statusCode());
        assertEquals(BAD_CREDENTIALS, response.jsonPath().getString("reason"));
    }

    @Test
    @DisplayName("Лишнее поле в теле запроса")
    public void additionalFieldInRequestBody() {
        String request = """
                {
                    "username" : "fake_user",
                    "password" : "wrong_password",
                    "comment" : "some text"
                }
                """;
        Response response = RestHelper.post(
                AUTH_URI,
                request);
        assertEquals(BAD_CREDENTIALS, response.jsonPath().getString("reason"));
        assertEquals(400, response.statusCode());
    }

    @Test
    @DisplayName("Неверный Content-Type")
    public void wrongContentType() {
        Response response = RestHelper.postWithXmlContentType(
                AUTH_URI,
                new AuthRequest("admin", "password123"));
        assertEquals(400, response.statusCode());
    }

    @Test
    @DisplayName("Объект в логине")
    public void objectInLogin() {
        String request = """
                {
                    "username" : { "value" : "admin" },
                    "password" : "password123"
                }
                """;
        Response response = RestHelper.post(
                AUTH_URI,
                request);
        assertEquals(BAD_CREDENTIALS, response.jsonPath().getString("reason"));
        assertEquals(400, response.statusCode());
    }

    @Test
    @DisplayName("Объект в пароле")
    public void objectInPassword() {
        String request = """
                {
                    "username" : "admin",
                    "password" : { "value" : "password123" }
                }
                """;
        Response response = RestHelper.post(
                AUTH_URI,
                request);
        assertEquals(BAD_CREDENTIALS, response.jsonPath().getString("reason"));
        assertEquals(400, response.statusCode());
    }
}
