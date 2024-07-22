package com.example.backend.model.response.event;

import com.example.backend.model.events.EventDetailsImpl;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateEventResponse {
    private EventDetailsImpl eventDetails;
}

