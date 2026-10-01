package internship.consumers;

import internship.messages.Message;
import internship.utils.DBHelper;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class TestConsumer {

    private final Consumer<String, Message> consumer;
    private final Map<String, Message> processedMessages = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(TestConsumer.class);
    private final DBHelper dbHelper;

    public TestConsumer(int mappedPort, DBHelper dbHelper) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:" + mappedPort);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "internship.serializers.MessageDeserializer");

        this.consumer = new KafkaConsumer<>(props);
        this.consumer.subscribe(Collections.singletonList("order-events"));
        this.dbHelper = dbHelper;
    }

    public TestConsumer(String bootstrapServers, DBHelper dbHelper) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "internship.serializers.MessageDeserializer");

        this.consumer = new KafkaConsumer<>(props);
        this.consumer.subscribe(Collections.singletonList("order-events"));
        // После subscribe
        this.consumer.poll(Duration.ofMillis(1000));  // ← Холодный старт
        this.dbHelper = dbHelper;
    }

    public List<Message> receiveMessages() {
        List<Message> sentMessages = new ArrayList<>();
        await().atMost(15, SECONDS).until(() -> {
            ConsumerRecords<String, Message> records = consumer.poll(Duration.ofMillis(500));
            for (ConsumerRecord<String, Message> record : records) {
                Header header = record.headers().lastHeader("X-Correlation-Id");
                if (header != null) {
                    String corrId = new String(header.value(), StandardCharsets.UTF_8);
                    if (processedMessages.get(corrId) == null) {
                        Message message = record.value();
                        dbHelper.createOrder(message.orderId(), message.status());
                        sentMessages.add(message);
                        processedMessages.put(corrId, message);
                        log.info("Message received: topic={}, partition={}, offset={}",
                                record.topic(), record.partition(), record.offset());
                    } else {
                        log.warn("Skipping duplicate message: orderId={}, corrId={}",
                                record.value().orderId(), corrId);
                    }
                } else {
                    log.warn("Message without X-Correlation-Id: {}", record.value());
                }
            }
            return !sentMessages.isEmpty();
        });
        return sentMessages;
    }

    public void close() {
        consumer.close();
    }
}
