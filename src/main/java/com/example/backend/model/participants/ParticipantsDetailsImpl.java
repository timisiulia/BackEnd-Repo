package com.example.backend.model.participants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ParticipantsDetailsImpl {

    private Long id;
    private Long eventId;
    private Long userId;
    private InviteStatus inviteStatus;

    public static ParticipantsDetailsImpl build(final Participant participant) {
        return builder()
                .id(participant.getId())
                .eventId(participant.getEvent().getId())
                .userId(participant.getUser().getId())
                .inviteStatus(participant.getInviteStatus())
                .build();

    }
}
