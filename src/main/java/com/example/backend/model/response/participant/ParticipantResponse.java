package com.example.backend.model.response.participant;

import com.example.backend.model.participants.InviteStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipantResponse {
    private Long id;
    private Long eventId;
    private Long userId;
    private InviteStatus inviteStatus;
}
