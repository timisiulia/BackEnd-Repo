package com.example.backend.model.response.failure;

public enum ErrorCode {
    UNAUTHORIZED ("Unauthorized"),
    USER_NOT_FOUND ("The user was not found"),
    INTERNAL_ERROR("Internal error"),

    EVENT_NOT_FOUND("The EVENT was not found");
    private String message;

    ErrorCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
