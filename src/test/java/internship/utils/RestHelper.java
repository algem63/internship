package internship.utils;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public final class RestHelper {

    private static final Logger log = LoggerFactory.getLogger(RestHelper.class);

    private RestHelper() {}

    public static Response get(String path) {
        return given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    public static Response post(String fullUrl, Object body) {
        return given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(body)
                .when()
                .post(fullUrl)
                .then()
                .extract()
                .response();
    }

    public static Response postWithXmlContentType(String fullUrl, Object body) {
        return given()
                .contentType(ContentType.XML)
                .log().all()
                .body(body)
                .when()
                .post(fullUrl)
                .then()
                .extract()
                .response();
    }

    public static Response postWithEmptyBody(String fullUrl) {
        return given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .post(fullUrl)
                .then()
                .extract()
                .response();
    }

    public static boolean validateSchema(Response response, String schemaPath) {
        try {
            response.then().assertThat()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
            return true;
        } catch (AssertionError e) {
            log.error("Schema validation failed: {}", e.getMessage());
            return false;
        }
    }
}
