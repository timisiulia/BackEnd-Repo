package com.example.backend.model.response.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
@Builder
public class GetUsersResponse {
    private List<UserDetails> user;
}
