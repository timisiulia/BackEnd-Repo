package com.example.backend.service;

import com.example.backend.model.events.Event;
import com.example.backend.model.participants.Participant;
import com.example.backend.model.participants.ParticipantsDetailsImpl;
import com.example.backend.model.request.participant.RespondEventRequest;
import com.example.backend.model.user.User;
import com.example.backend.repository.EventRepository;
import com.example.backend.repository.ParticipantRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ParticipantService {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    private final ParticipantRepository participantRepository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    public ParticipantsDetailsImpl createParticipant(final ParticipantsDetailsImpl initialParticipant) {
        Event event = eventRepository.findById(initialParticipant.getEventId()).orElseThrow();
        User user = userRepository.findById(initialParticipant.getUserId()).orElseThrow();

        Participant newParticipant = Participant.builder()
                .event(event)
                .user(user)
                .inviteStatus(initialParticipant.getInviteStatus())
                .build();

        Participant createdParticipant = participantRepository.save(newParticipant);

        return ParticipantsDetailsImpl.builder()
                .id(createdParticipant.getId())
                .eventId(createdParticipant.getEvent().getId())
                .userId(createdParticipant.getUser().getId())
                .inviteStatus(createdParticipant.getInviteStatus())
                .build();
    }

    public ParticipantsDetailsImpl respondToEvent(final Long userId, final RespondEventRequest payload) {
        Participant participant = participantRepository.findParticipantByEventIdAndUserId(payload.getEventId(), userId);

        Participant newParticipant = Participant.builder()
                .id(participant.getId())
                .event(participant.getEvent())
                .user(participant.getUser())
                .inviteStatus(payload.getInviteStatus())
                .build();

        Participant createdParticipant = participantRepository.save(newParticipant);

        Event event = eventRepository.findById(createdParticipant.getEvent().getId()).orElseThrow();

        simpMessagingTemplate.convertAndSend("/topic/new-accept/" + event.getUser().getId(), "user-ul " + participant.getUser().getId() + " a zis " + createdParticipant.getInviteStatus());

        return ParticipantsDetailsImpl.builder()
                .id(createdParticipant.getId())
                .eventId(createdParticipant.getEvent().getId())
                .userId(createdParticipant.getUser().getId())
                .inviteStatus(createdParticipant.getInviteStatus())
                .build();
    }
}