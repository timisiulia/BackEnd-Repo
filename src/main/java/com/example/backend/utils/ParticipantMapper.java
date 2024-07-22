package com.example.backend.utils;

import com.example.backend.model.participants.Participant;
import com.example.backend.model.participants.ParticipantsDetailsImpl;
import com.example.backend.model.response.participant.ParticipantResponse;

public class ParticipantMapper {

//    public static Participant toParticipant (final ParticipantsDetailsImpl participantsDetails){
//        return Participant.builder()
//                .id()
//                .
//                .build();
//    }
    public static ParticipantResponse toParticipantResponse (final Participant participant){
        return ParticipantResponse.builder()
                .id(participant.getId())
                .eventId(participant.getEvent().getId())
                .userId(participant.getUser().getId())
                .inviteStatus(participant.getInviteStatus())
                .build();

    }
    public static ParticipantsDetailsImpl fromParticipant(final Participant participant){
        return ParticipantsDetailsImpl.builder()
                .id(participant.getId())
                .eventId(participant.getEvent().getId())
                .userId(participant.getUser().getId())
                .inviteStatus(participant.getInviteStatus())
                .build();
    }

}
