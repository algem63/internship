package internship.tests.week3.task3;

import internship.config.Config;
import internship.utils.DBHelper;
import internship.utils.RestHelper;
import internship.utils.TraceIdManager;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class AsyncBlackBoxTest {

    private static final Logger log = LoggerFactory.getLogger(AsyncBlackBoxTest.class);

    private static final DBHelper dbHelper = new DBHelper(
            Config.INSTANCE.localDatabaseUrl(),
            Config.INSTANCE.localDatabaseUsername(),
            Config.INSTANCE.localDatabasePassword());

    @BeforeAll
    public static void setUp() {
        dbHelper.createEventsTable();
    }

    @AfterAll
    public static void tearDown() {
        dbHelper.dropEventsTable();
        dbHelper.close();
    }

    @Test
    @DisplayName("Корректный запрос")
    public void positiveCheck() {
        String requestId = UUID.randomUUID().toString();

        String traceId = TraceIdManager.get();  // ← Для логов
        log.info("Starting test with traceId={}", traceId);

        Response response = RestHelper.postWithCorrelationIdHeader(
                Config.INSTANCE.triggerUrl(),
                requestId);
        assertEquals(200, response.statusCode());
        await().atMost(10, SECONDS)
                .pollInterval(500, MILLISECONDS)
                .untilAsserted(() -> {
                    String status = dbHelper.checkEventsTable(requestId);
                    log.info("Checking status for requestId={}: {}", requestId, status);
                    assertEquals("PROCESSED", status);
                });
    }

    @Test
    @DisplayName("Некорректный запрос, запись НЕ появляется")
    void negativeCheck() {
        String requestId = UUID.randomUUID().toString();

        Response response = RestHelper.postWithEmptyBody(Config.INSTANCE.triggerUrl());
        assertNotEquals(200, response.statusCode());

        await().during(3, SECONDS)
                .atMost(5, SECONDS)
                .untilAsserted(() -> {
                    String status = dbHelper.checkEventsTable(requestId);
                    assertNull(status, "Запись НЕ должна появиться для некорректного запроса");
                });
    }
}
