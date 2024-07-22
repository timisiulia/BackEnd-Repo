package com.example.backend.utils;


import com.example.backend.model.user.User;
import com.example.backend.model.user.UserDetailsImpl;
import com.example.backend.model.user.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserMapper {

    public static User toUser(final UserDetailsImpl userDetails) {
        return User.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .password(userDetails.getPassword())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .role(mapAuthorityToUserRole(userDetails.getAuthorities()))
                .email(userDetails.getEmail())
                .build();
    }

    public static UserDetails fromUser(final User user) {
        return UserDetailsImpl.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .authorities(List.of(user.getRole().toAuthority()))
                .password(user.getPassword())
                .build();
    }

    private static UserRole mapAuthorityToUserRole(final Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .map(UserRole::fromString)
                .findFirst()
                .orElse(null);
    }
}
