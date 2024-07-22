package com.example.backend.model.request;

import com.example.backend.model.user.UserRole;
import lombok.Data;

@Data
public class RegisterRequest {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String username;
    private final String password;
    private final UserRole role;
}
