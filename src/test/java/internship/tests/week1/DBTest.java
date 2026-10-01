package internship.tests.week1;

import internship.dto.OrderStatusDTO;
import internship.utils.DBHelper;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class DBTest {

    private static DBHelper dbHelper;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withExposedPorts(5432);

    @BeforeAll
    public static void setUp() {
        dbHelper = new DBHelper(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        dbHelper.createUsersTable();
        dbHelper.createOrdersTable();
    }

    @AfterAll
    public static void tearDown() {
        dbHelper.close();
    }

    @BeforeEach
    public void prepareTestData() throws SQLException {
        dbHelper.cleanUsersAndOrders();
        dbHelper.insertTestUsers();
        dbHelper.insertTestOrders();
    }

    @Test
    @DisplayName("Найти пользователя по части почты (LIKE '%@test%') и убедиться, что он один")
    public void findSingleUserByEmail() {
        int amountOfUsers = dbHelper.findUserByMail("%@api.test");
        assertEquals(1, amountOfUsers);
    }

    @Test
    @DisplayName("Вывести список активных пользователей, созданных за последнюю неделю (используя NOW() и интервалы)")
    public void findActiveUsersForTheLastWeek() {
        int amountOfUsers = dbHelper.findActiveUsersForTheLastWeek();
        assertEquals(1, amountOfUsers);
    }

    @Test
    @DisplayName("Выбрать всех пользователей, у которых есть заказы на сумму > 1000 (JOIN)")
    public void findUsersWithSpecificAmountOfOrders() {
        int amountOfUsers = dbHelper.findUsersWithSpecificAmountOfOrders();
        assertEquals(7, amountOfUsers);
    }

    @Test
    @DisplayName("Вставить нового пользователя и его заказ (в одной транзакции)")
    public void insertANewUserAndItsOrder() {
        dbHelper.insertUserAndOrder();
        int amountOfUsers = dbHelper.checkIfCreatedUserExists();
        assertEquals(1, amountOfUsers);
    }

    @Test
    @DisplayName("Обновить статус заказа по email пользователя")
    public void updateUserStatusByEmail() {
        dbHelper.updateOrderStatus("pat.moore@email.com");
        List<OrderStatusDTO> result = dbHelper.checkIfOrderStatusChanged();
        assertEquals(1, result.size());
        OrderStatusDTO orderStatus = result.getFirst();
        assertEquals("processing", orderStatus.orderstatus());
        assertEquals(1, orderStatus.amountOfOrders());
    }
}
