package com.example.backend.controller;

import com.example.backend.model.response.failure.ErrorCode;
import com.example.backend.model.response.failure.FailureResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import static java.time.LocalDateTime.now;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UsernameNotFoundException.class)
    public FailureResponse handleUsernameNotFoundException(final UsernameNotFoundException ex) {
        return FailureResponse.builder()
                .message(ex.getMessage())
                .code(ErrorCode.UNAUTHORIZED)
                .timestamp(now())
                .build();
    }
}
