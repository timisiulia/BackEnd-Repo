package com.example.backend.model.request.email;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailRequest {
    private String recipient;
    private String subject;
    private String content;

}

