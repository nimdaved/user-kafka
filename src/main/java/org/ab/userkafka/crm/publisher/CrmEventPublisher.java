package org.ab.userkafka.crm.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ab.userkafka.common.domain.ChangeOriginator;
import org.ab.userkafka.crm.domain.CrmUser;
import org.ab.userkafka.event.EventPublisher;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.ab.userkafka.crm.CrmKafkaConstants.*;

@Component
public class CrmEventPublisher extends EventPublisher {


    public CrmEventPublisher(KafkaTemplate<Long, CrmUser> kafkaTemplate, ObjectMapper objectMapper) {
        super(kafkaTemplate, objectMapper);
    }

    @Override
    protected String topicName() {
        return CRM_USER_TOPIC;
    }

    @Override
    protected String createdEventValue() {
        return "CRM_USER_CREATED";
    }

    @Override
    protected String updatedEventValue() {
        return "CRM_USER_UPDATED";
    }

    @Override
    protected String headerEventSource() {
        return CRM_HEADER_EVENT_SOURCE;
    }

    @Override
    protected String headerEventType() {
        return CRM_HEADER_EVENT_TYPE;
    }
}
