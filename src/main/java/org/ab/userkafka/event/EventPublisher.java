package org.ab.userkafka.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ab.userkafka.common.domain.ChangeOriginator;
import org.ab.userkafka.common.domain.User;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
public abstract class EventPublisher <T extends User>{
    private final KafkaTemplate<Long, T> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public EventPublisher(KafkaTemplate<Long, T> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void userUpdated(T user, ChangeOriginator source) {
        sendEvent(user, source, updatedEventValue());
    }

    private void sendEvent(T user, ChangeOriginator source, String eventType) {
//        try {
            var record = new ProducerRecord<>(topicName(), user.getId(),
                    //objectMapper.writeValueAsString(user)
                    user
            );
            record.headers().add(headerEventSource(), source.name().getBytes(StandardCharsets.UTF_8));
            record.headers().add(headerEventType(), eventType.getBytes(StandardCharsets.UTF_8));


            var future = kafkaTemplate.send(record);
            future.whenComplete((r, e) -> Optional.ofNullable(e)
                    .ifPresentOrElse(ex->
                            log.error("Could not send event", ex),
                    () -> log.info("Sent event: {} for user: {}", r.getRecordMetadata(), user)

            ));
//        }
//        catch (JsonProcessingException e) {
//            throw new IllegalArgumentException("Could not parse " + user, e);
//        }
    }

    public void userCreated(T user, ChangeOriginator source) {
        sendEvent(user, source, createdEventValue());
    }

    protected abstract String topicName();

    protected abstract String createdEventValue();

    protected abstract String updatedEventValue();
    protected abstract String headerEventSource();
    protected abstract String headerEventType();
}
