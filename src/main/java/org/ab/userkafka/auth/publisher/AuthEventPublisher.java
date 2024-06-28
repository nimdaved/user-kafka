package org.ab.userkafka.auth.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ab.userkafka.auth.domain.AuthUser;
import org.ab.userkafka.event.EventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static org.ab.userkafka.auth.AuthKafkaConstants.*;

@Component
public class AuthEventPublisher extends EventPublisher {
    public AuthEventPublisher(KafkaTemplate<Long, AuthUser> kafkaTemplate, ObjectMapper objectMapper) {
        super(kafkaTemplate, objectMapper);
    }

    @Override
    protected String topicName() {
        return AUTH_USER_TOPIC;
    }

    @Override
    protected String createdEventValue() {
        return "AUTH_USER_CREATED";
    }

    @Override
    protected String updatedEventValue() {
        return "AUTH_USER_UPDATED";
    }

    @Override
    protected String headerEventSource() {
        return AUTH_HEADER_EVENT_SOURCE;
    }

    @Override
    protected String headerEventType() {
        return AUTH_HEADER_EVENT_TYPE;
    }
}
