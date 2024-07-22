package com.example.backend.model.response.failure;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FailureResponse {
    private LocalDateTime timestamp;
    private ErrorCode code;
    private String message;
}
