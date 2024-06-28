package org.ab.userkafka.auth.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ab.userkafka.auth.domain.AuthUser;
import org.ab.userkafka.auth.publisher.AuthEventPublisher;
import org.ab.userkafka.auth.repository.AuthUserRepository;
import org.ab.userkafka.common.domain.ChangeOriginator;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {
    private final AuthUserRepository authUserRepository;
    private final AuthEventPublisher authEventPublisher;

    public AuthService(AuthUserRepository authUserRepository, AuthEventPublisher authEventPublisher) {
        this.authUserRepository = authUserRepository;
        this.authEventPublisher = authEventPublisher;
    }


    @Transactional
    public AuthUser changeUser(AuthUser user, ChangeOriginator source) {
        AuthUser savedUser;

        if (user.isNewUser() || authUserRepository.findById(user.getId()).filter(existingUser -> !existingUser.equals(user)).isPresent()) {
            savedUser = authUserRepository.save(user);
            if (user.isNewUser()) {
                authEventPublisher.userCreated(savedUser, source);
            } else {
                authEventPublisher.userUpdated(savedUser, source);
            }

        } else {
            String msg = String.format("User %s either already exists on the system, or has invalid id", user);
            log.warn(msg);
            throw new IllegalArgumentException(msg);
        }

        return savedUser;
    }

    public AuthUser findById(Long id) {
        return authUserRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Could not find user with id: " + id));
    }
}
