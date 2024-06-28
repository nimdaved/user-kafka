package org.ab.userkafka.common.domain;

import java.util.stream.Stream;

public enum ChangeOriginator {
    AUTH,
    CRM;

    public static ChangeOriginator from(String s) {
        return Stream.of(values())
                .filter(n -> n.name().equalsIgnoreCase(s)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown event source: " + s));

    }
}
