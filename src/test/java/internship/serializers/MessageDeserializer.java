package internship.serializers;

import internship.messages.Message;
import org.apache.kafka.common.serialization.Deserializer;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

public class MessageDeserializer implements Deserializer<Message> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Message deserialize(String topic, byte[] data) {
        return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), Message.class);
    }
}
