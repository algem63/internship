package internship.tests.week3;

import internship.utils.DBHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class InsertNewUserTest {

    private static DBHelper dbHelper;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withExposedPorts(5432);

    @BeforeAll
    public static void setUp() {
        dbHelper = new DBHelper(postgres.getMappedPort(5432));
        dbHelper.createUsersTable();
    }

    @AfterAll
    public static void tearDown() {
        dbHelper.close();
    }

    @Test
    public void insertNewUser() {
        String email = dbHelper.insertNewUser();
        assertEquals("john_doe@gmail.com", email);
    }
}
