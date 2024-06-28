package org.ab.userkafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.ab.userkafka.common.domain.ChangeOriginator;
import org.ab.userkafka.crm.domain.CrmUser;
import org.ab.userkafka.event.EventHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import static org.ab.userkafka.crm.CrmKafkaConstants.*;


@Component
@Slf4j
public class CrmEventListener {
    private final EventHandler eventHandler;

    public CrmEventListener(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @KafkaListener(topics = CRM_USER_TOPIC, groupId = CRM_USER_GROUP)
    public void onCrmEvent(CrmUser crmUser, @Header(CRM_HEADER_EVENT_SOURCE) String eventSource,
                           @Header(CRM_HEADER_EVENT_TYPE) String eventType) {
        log.info("Received: {}; {}; {}", eventSource, eventType, crmUser);
        eventHandler.onCrmEvent(crmUser, ChangeOriginator.from(eventSource));
    }
}
