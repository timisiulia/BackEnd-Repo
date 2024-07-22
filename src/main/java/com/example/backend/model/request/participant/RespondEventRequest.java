package com.example.backend.model.request.participant;

import com.example.backend.model.participants.InviteStatus;
import lombok.Data;

@Data
public class RespondEventRequest {
    private Long eventId;
    private InviteStatus inviteStatus;
}
