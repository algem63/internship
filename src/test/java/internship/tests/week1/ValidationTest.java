package internship.tests.week1;

import internship.dto.UserDTO;
import internship.utils.RestHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationTest {

    private final String BASE_URI = "https://reqres.in/api";

    @Test
    void validateResponse1() {
        Response response = RestHelper.get(BASE_URI + "/users?page=2");
        assertEquals(200, response.statusCode());
        assertTrue(RestHelper.validateSchema(response, "Schema-1.json"),
                "Схема ответа не соответствует ожидаемой");
    }

    @Test
    void validateResponse2() {
        UserDTO requestBody = new UserDTO("morpheus", "leader");
        Response response = RestHelper.post(BASE_URI + "/users", requestBody);
        assertEquals(201, response.statusCode());
        assertTrue(RestHelper.validateSchema(response, "Schema-2.json"),
                "Схема ответа не соответствует ожидаемой");
    }
}
