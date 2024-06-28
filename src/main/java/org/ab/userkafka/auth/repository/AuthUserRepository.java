package org.ab.userkafka.auth.repository;

import org.ab.userkafka.auth.domain.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserRepository  extends JpaRepository<AuthUser, Long> {
}
