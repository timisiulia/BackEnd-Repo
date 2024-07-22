package com.example.backend.model.request.event;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class IntersectionEventRequest {
    private List<Long> userIds;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
