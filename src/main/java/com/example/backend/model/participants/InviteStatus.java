package com.example.backend.model.participants;

import static java.util.Arrays.stream;

public enum InviteStatus {
    PENDING,
    ACCEPTED,
    MAYBE,
    DECLINED;

    public static InviteStatus fromString(final String value) {
        return stream(values())
                .filter(inviteStatus -> inviteStatus.name().equals(value))
                .findFirst()
                .orElse(null);
    }
}
