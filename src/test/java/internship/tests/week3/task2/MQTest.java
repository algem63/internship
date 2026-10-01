package internship.tests.week3.task2;

import internship.consumers.TestConsumer;
import internship.messages.Message;
import internship.producers.TestProducer;
import internship.utils.DBHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.redpanda.RedpandaContainer;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class MQTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withExposedPorts(5432);

    @Container
    private static final RedpandaContainer redpanda = new RedpandaContainer(
            "docker.redpanda.com/redpandadata/redpanda:v23.1.2");

    private static TestProducer producer;
    private static TestConsumer consumer;

    private static DBHelper dbHelper;

    @BeforeAll
    public static void setUp() {
        Integer mappedPostgresPort = postgres.getMappedPort(5432);
        Integer mappedRedPandaPort = redpanda.getMappedPort(9092);
        try {
            redpanda.execInContainer("rpk topic create order-events");
            Thread.sleep(5000);
            dbHelper = new DBHelper(
                    postgres.getJdbcUrl(),
                    postgres.getUsername(),
                    postgres.getPassword()
            );
            dbHelper.createOrdersTableForMQTest();

            producer = new TestProducer(redpanda.getBootstrapServers());
            consumer = new TestConsumer(redpanda.getBootstrapServers(), dbHelper);
            Thread.sleep(3000);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void tearDown() {
        dbHelper.dropOrdersTable();
        dbHelper.close();

        producer.close();
        consumer.close();
    }

    @Test
    void sendMessageToQueueAndCheckExistence() {
        Message messageToSend = new Message(123, "PAID");
        for (int i = 0; i < 3; i++) {
            producer.sendMessage(messageToSend, "58bee901-c16f-4ecc-84b4-2f7bdf570262");
        }
        List<Message> receimedMessages = consumer.receiveMessages();
        assertEquals(1, receimedMessages.size());
        Message receivedMessage = receimedMessages.getFirst();
        assertEquals(123, receivedMessage.orderId());
        assertEquals("PAID", receivedMessage.status());
        int ordersCount = dbHelper.countOrders(123);
        assertEquals(1, ordersCount);
    }
}
