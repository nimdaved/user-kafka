package org.ab.userkafka.event;

import lombok.extern.slf4j.Slf4j;
import org.ab.userkafka.auth.domain.AuthUser;
import org.ab.userkafka.auth.service.AuthService;
import org.ab.userkafka.common.domain.ChangeOriginator;
import org.ab.userkafka.common.domain.User;
import org.ab.userkafka.crm.domain.CrmUser;
import org.ab.userkafka.crm.service.CrmService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventHandler {

    private final AuthService authService;
    private final CrmService crmService;

    public EventHandler(AuthService authService, CrmService crmService) {
        this.authService = authService;
        this.crmService = crmService;
    }


    public void onCrmEvent(User changedUser, ChangeOriginator eventSource) {
        log.info("CRM event from {} event source", eventSource);
        if (ChangeOriginator.AUTH != eventSource) {
            AuthUser user = AuthUser.builder()
                    .user(changedUser).build();
            authService.changeUser(user, eventSource);
        } else {
            emptyEventSink(changedUser, eventSource);
        }
    }

    public void onAuthEvent(User changedUser, ChangeOriginator eventSource) {
        log.info("AUTH event from {} event source", eventSource);
        if (ChangeOriginator.CRM != eventSource) {
            CrmUser user = CrmUser.builder()
                    .user(changedUser).build();
            crmService.changeUser(user, eventSource);
        } else {
            emptyEventSink(changedUser, eventSource);
        }
    }

    private void emptyEventSink(User changedUser, ChangeOriginator eventSource) {
        log.info("Event propagation is not required for the user: {} changed by {} event source",
                changedUser, eventSource);
    }
}
