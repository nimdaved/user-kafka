package org.ab.userkafka.crm.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ab.userkafka.common.domain.User;
import org.ab.userkafka.crm.domain.Address.Address;
import org.hibernate.annotations.BatchSize;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmUser implements User {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    private String lastName;
    @NotBlank
    private String email;
    @Size(min = 3, max = 30)
    private String username;
    @Size(max = 50)
    private String firstName;

    @ManyToMany
    @JoinTable(
            name = "user_addresses",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "address_id", referencedColumnName = "id")})

    @BatchSize(size = 20)
    @Builder.Default
    private Set<Address> addresses = new LinkedHashSet<>();


    @Override
    public String getNicName() {
        return this.getUsername();
    }

    public static class CrmUserBuilder {
        public CrmUserBuilder user(User user) {
            return id(user.getId()).
                    email(user.getEmail())
                    .username(user.getNicName())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName());
        }

    }
}
