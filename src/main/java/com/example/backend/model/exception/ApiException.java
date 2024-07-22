package com.example.backend.model.exception;

import com.example.backend.model.response.failure.ErrorCode;
import lombok.Builder;
import lombok.Data;

import static com.example.backend.model.response.failure.ErrorCode.INTERNAL_ERROR;


@Data
@Builder
public class ApiException extends RuntimeException {

    protected final ErrorCode errorCode;

    public ApiException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException() {
        super(INTERNAL_ERROR.getMessage());
        this.errorCode = INTERNAL_ERROR;
    }

    public ApiException(final String message) {
        super(message);
        this.errorCode = INTERNAL_ERROR;
    }
}
