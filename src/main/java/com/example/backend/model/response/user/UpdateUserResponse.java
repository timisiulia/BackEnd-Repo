package com.example.backend.model.response.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
public class UpdateUserResponse {
    private UserDetails user;

}
