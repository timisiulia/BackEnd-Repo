package com.example.backend.model.request.event;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateEventRequest {
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String description;

    private String location;

    private Boolean recurrency;

    private Integer recurrencyPeriod;

    private List<Long> userIds;
}
