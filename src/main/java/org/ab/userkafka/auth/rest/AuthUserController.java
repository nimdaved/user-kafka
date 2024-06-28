package org.ab.userkafka.auth.rest;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ab.userkafka.auth.domain.AuthUser;
import org.ab.userkafka.auth.service.AuthService;
import org.ab.userkafka.common.domain.ChangeOriginator;
import org.ab.userkafka.crm.domain.CrmUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth/users")
@Slf4j
public class AuthUserController {
    private final AuthService authService;

    public AuthUserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    AuthUser upsert(@RequestBody @Valid AuthUser user) {
        log.info("user: {}", user);
        return authService.changeUser(user, ChangeOriginator.AUTH);
    }

    @GetMapping("/{id}")
    AuthUser findById(@PathVariable Long id) {
        return authService.findById(id);
    }

}
