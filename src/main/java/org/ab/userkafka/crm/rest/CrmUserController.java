package org.ab.userkafka.crm.rest;

import jakarta.validation.Valid;
import org.ab.userkafka.common.domain.ChangeOriginator;
import org.ab.userkafka.crm.domain.CrmUser;
import org.ab.userkafka.crm.service.CrmService;
import org.apache.catalina.UserDatabase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/crm/users")
public class CrmUserController {

    private final CrmService crmService;

    public CrmUserController(CrmService crmService) {
        this.crmService = crmService;
    }

    @PostMapping
    CrmUser upsert(@Valid CrmUser user) {
        return crmService.changeUser(user, ChangeOriginator.CRM);
    }

    @GetMapping("/{id}")
    CrmUser findById(@PathVariable Long id) {
        return crmService.findById(id);
    }

}
