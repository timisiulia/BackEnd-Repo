package com.example.backend.model.request.user;

import lombok.Data;

@Data
public class UpdateUserRequest {

    private String lastName;
    private String firstName;
    private String email;
}
