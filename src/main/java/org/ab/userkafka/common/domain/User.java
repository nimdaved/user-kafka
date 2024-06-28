package org.ab.userkafka.common.domain;

import java.util.Objects;

public interface User {
    Long getId();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getNicName();

    default
    boolean isNewUser() {
        return Objects.isNull(getId());
    }

    default boolean hasSameValues (User other) {
        return Objects.equals(getId(), other.getId())
                && Objects.equals(getEmail(), other.getEmail())
                && Objects.equals(getNicName(), other.getNicName())
                && Objects.equals(getFirstName(), other.getFirstName())
                && Objects.equals(getLastName(), other.getLastName())
                ;
    }
}
