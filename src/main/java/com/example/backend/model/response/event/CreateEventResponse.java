package com.example.backend.model.response.event;

import com.example.backend.model.participants.Participant;
import com.example.backend.model.participants.ParticipantsDetailsImpl;
import com.example.backend.model.user.User;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CreateEventResponse {
    private final Long id;
    private final Long user;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private final String description;
    private final String location;
    private final Boolean recurrency;
    private final Integer recurrencyPeriod;
    private final List<ParticipantsDetailsImpl> participants;
}
