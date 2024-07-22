package com.example.backend.model.response;

import com.example.backend.model.user.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String username;
    private final UserRole role;

}
