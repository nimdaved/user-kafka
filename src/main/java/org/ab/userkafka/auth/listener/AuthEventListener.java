package org.ab.userkafka.auth.listener;

import lombok.extern.slf4j.Slf4j;
import org.ab.userkafka.auth.domain.AuthUser;
import org.ab.userkafka.common.domain.ChangeOriginator;
import org.ab.userkafka.crm.domain.CrmUser;
import org.ab.userkafka.event.EventHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import static org.ab.userkafka.auth.AuthKafkaConstants.*;
import static org.ab.userkafka.crm.CrmKafkaConstants.*;
import static org.ab.userkafka.crm.CrmKafkaConstants.CRM_HEADER_EVENT_TYPE;

@Component
@Slf4j
public class AuthEventListener {
    private final EventHandler eventHandler;

    public AuthEventListener(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @KafkaListener(topics = AUTH_USER_TOPIC, groupId = AUTH_USER_GROUP)
    public void onAuthEvent(AuthUser user, @Header(AUTH_HEADER_EVENT_SOURCE) String eventSource,
                           @Header(AUTH_HEADER_EVENT_TYPE) String eventType) {
        log.info("Received: {}; {}; {}", eventSource, eventType, user);
        eventHandler.onAuthEvent(user, ChangeOriginator.from(eventSource));
    }
}
