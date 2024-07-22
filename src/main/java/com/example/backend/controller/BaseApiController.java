package com.example.backend.controller;

import com.example.backend.model.exception.ApiException;
import com.example.backend.model.response.failure.ErrorCode;
import com.example.backend.model.user.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;

import static java.util.Optional.ofNullable;

public class BaseApiController {

    protected UserDetailsImpl resolveUserDetailsFromPrincipal(final Principal principal) {
        return ofNullable(principal)
                .map(UsernamePasswordAuthenticationToken.class::cast)
                .map(UsernamePasswordAuthenticationToken::getPrincipal)
                .filter(UserDetailsImpl.class::isInstance)
                .map(UserDetailsImpl.class::cast)
                .orElseThrow(() -> new ApiException("Cannot resolve user details from principal"));
    }
}
