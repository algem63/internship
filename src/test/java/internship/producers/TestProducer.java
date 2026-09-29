package internship.producers;

import internship.messages.Message;
import internship.serializers.MessageSerializer;
import internship.utils.TraceIdManager;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class TestProducer {

    private Producer<String, Message> producer;
    private static final Logger log = LoggerFactory.getLogger(TestProducer.class);

    public TestProducer(int mappedPort) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:" + mappedPort);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MessageSerializer.class.getName());
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        producer = new KafkaProducer<>(props);
    }

    public void sendMessage(Message message, String corrId) {
        ProducerRecord<String, Message> record = new ProducerRecord<>("order-events", "key", message);
        record.headers().add("X-Correlation-Id", corrId.getBytes(StandardCharsets.UTF_8));
        record.headers().add("X-Trace-Id",
                TraceIdManager.get().getBytes(StandardCharsets.UTF_8));
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                log.error("Failed to send message: orderId={}, corrId={}",
                        message.orderId(), corrId, exception);
            } else {
                log.info("Message sent: topic={}, partition={}, offset={}",
                        metadata.topic(), metadata.partition(), metadata.offset());
            }
        });
    }

    public void close() {
        producer.close();
    }
}
