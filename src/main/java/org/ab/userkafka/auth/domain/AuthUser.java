package org.ab.userkafka.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ab.userkafka.common.domain.User;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser implements User {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    private String lastName;
    @NotBlank
    @Size(min = 4, max = 80)
    private String email;
    @Size(min = 3, max = 30)
    private String username;
    @Size(max = 50)
    private String firstName;
    @Size(max = 128)
    private String passwordHash;

    @Override
    public String getNicName() {
        return getUsername();
    }

    public static class AuthUserBuilder {
        public AuthUserBuilder user(User user) {
            return id(user.getId()).
                    email(user.getEmail())
                    .username(user.getNicName())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName());
        }

    }


}
