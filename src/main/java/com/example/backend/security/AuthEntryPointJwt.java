package com.example.backend.security;

import com.example.backend.model.response.failure.ErrorCode;
import com.example.backend.model.response.failure.FailureResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;

import static java.time.LocalDateTime.now;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Not actually an entry point, as we don't redirect the user to login page.
 * Handles bad credentials errors
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {
            FailureResponse responseBody = FailureResponse.builder()
                    .message("Bad credentials")
                    .code(ErrorCode.UNAUTHORIZED)
                    .timestamp(now())
                    .build();
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(SC_UNAUTHORIZED);
            outputStream.write(objectMapper.writeValueAsBytes(responseBody));
            outputStream.flush();
        }
    }
}
