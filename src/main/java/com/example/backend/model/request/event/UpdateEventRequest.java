package com.example.backend.model.request.event;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UpdateEventRequest {
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String description;

    private String location;

    private Boolean recurrency;

    private Integer recurrencyPeriod;
}
