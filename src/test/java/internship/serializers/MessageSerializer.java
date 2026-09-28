package internship.serializers;

import internship.messages.Message;
import org.apache.kafka.common.serialization.Serializer;
import tools.jackson.databind.ObjectMapper;

public class MessageSerializer implements Serializer<Message> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Message data) {
        return objectMapper.writeValueAsBytes(data);
    }
}
