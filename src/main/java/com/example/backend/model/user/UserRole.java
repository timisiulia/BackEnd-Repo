package com.example.backend.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static java.util.Arrays.stream;

public enum UserRole {
    ADMIN,
    USER;

    public static UserRole fromString(final String value) {
        return stream(values())
                .filter(userRole -> userRole.name().equals(value))
                .findFirst()
                .orElse(null);
    }

    public GrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority(name());
    }
}
