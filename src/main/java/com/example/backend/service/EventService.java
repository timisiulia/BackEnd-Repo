package com.example.backend.service;

import com.example.backend.model.email.EmailPayload;
import com.example.backend.model.events.Event;
import com.example.backend.model.events.EventDetailsImpl;
import com.example.backend.model.events.EventIntersection;
import com.example.backend.model.exception.ApiException;
import com.example.backend.model.participants.InviteStatus;
import com.example.backend.model.participants.Participant;
import com.example.backend.model.participants.ParticipantsDetailsImpl;
import com.example.backend.model.request.email.EmailRequest;
import com.example.backend.model.request.event.CreateEventRequest;
import com.example.backend.model.request.event.IntersectionEventRequest;
import com.example.backend.model.request.event.UpdateEventRequest;
import com.example.backend.model.response.event.CreateEventResponse;
import com.example.backend.model.user.User;
import com.example.backend.repository.EventRepository;
import com.example.backend.repository.ParticipantRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.utils.EventMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.backend.model.response.failure.ErrorCode.EVENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class EventService {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;

    private final EmailService emailService;

    public List<CreateEventResponse> getEvents(final UserDetails initialUser) {
        User user = userRepository.findUserByUsername(initialUser.getUsername()).orElseThrow();
        boolean isAdmin = Objects.equals(user.getRole().toString(), "ADMIN");

        if (isAdmin) {
            return eventRepository.findAll().stream().map(EventMapper::toResponseEvent).collect(Collectors.toList());
        }

        List<Participant> invites = participantRepository.findParticipantsByUserId(user.getId());

        List<Event> eventsToAttend = invites.stream().map((p) -> eventRepository.findById(p.getEvent().getId()).orElseThrow()).toList();

        return Stream.concat(eventRepository.findEventsByUserId(user.getId())
                        .stream(), eventsToAttend.stream())
                .map(EventMapper::toResponseEvent)
                .collect(Collectors.toList());
    }

    public CreateEventResponse createNewEvent(final UserDetails initialUser, final CreateEventRequest event) {
        User user = userRepository.findUserByUsername(initialUser.getUsername()).orElseThrow();
        Event newEvent = Event.builder()
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .description(event.getDescription())
                .location(event.getLocation())
                .recurrency(event.getRecurrency())
                .recurrencyPeriod(event.getRecurrencyPeriod())
                .user(user)
                .build();
        Event createdEvent = eventRepository.save(newEvent);

        List<ParticipantsDetailsImpl> participants = event.getUserIds().stream().map(id -> {
            User invitee = userRepository.findById(id).orElseThrow();
            Participant newParticipant = Participant.builder()
                    .event(createdEvent)
                    .user(invitee)
                    .inviteStatus(InviteStatus.PENDING)
                    .build();

            Participant created = participantRepository.save(newParticipant);

            String body = EmailPayload.generateEmailBody(createdEvent);

            EmailRequest email = EmailRequest.builder()
                    .recipient(invitee.getEmail())
                    .content(body)
                    .subject(createdEvent.getDescription())
                    .build();

            try {
                emailService.sendEmail(email);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            simpMessagingTemplate.convertAndSend("/topic/new-event/" + created.getUser().getId(), "ai un eveniment nou");

            return ParticipantsDetailsImpl.builder()
                    .id(created.getId())
                    .eventId(created.getEvent().getId())
                    .userId(created.getUser().getId())
                    .inviteStatus(created.getInviteStatus())
                    .build();
        }).toList();

        return CreateEventResponse.builder()
                .id(createdEvent.getId())
                .startDate(createdEvent.getStartDate())
                .endDate(createdEvent.getEndDate())
                .description(createdEvent.getDescription())
                .location(createdEvent.getLocation())
                .recurrency(createdEvent.getRecurrency())
                .recurrencyPeriod(createdEvent.getRecurrencyPeriod())
                .user(createdEvent.getUser().getId())
                .participants(participants)
                .build();
    }

    public List<EventIntersection> getIntersection(final UserDetails initialUser, final IntersectionEventRequest payload) {
        System.out.println(payload.getStartDate());
        System.out.println(payload.getEndDate());
        User user = userRepository.findUserByUsername(initialUser.getUsername()).orElseThrow();
        List<Long> list = new ArrayList<Long>(payload.getUserIds());
        list.add(user.getId());

        System.out.println("the mighty list:");
        System.out.println(list);

        return this.eventRepository.findEventsIntersectionForUsers(list, payload.getStartDate(), payload.getEndDate());
    }

    public Event updateEvent(final UpdateEventRequest request, final Long id) {
        return eventRepository.findById(id).map(
                        e -> {
                            e.setStartDate(request.getStartDate());
                            e.setEndDate(request.getEndDate());
                            e.setDescription(request.getDescription());
                            e.setLocation(request.getLocation());
                            e.setRecurrency(request.getRecurrency());
                            e.setRecurrencyPeriod(request.getRecurrencyPeriod());
                            return e;
                        }
                )
                .map(eventRepository::save)
                .orElseThrow(() -> new ApiException(EVENT_NOT_FOUND));
    }

    public void deleteEvent(final Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();

        List<Participant> participants = event.getPaticipants();

        participantRepository.deleteAll(participants);

        eventRepository.deleteById(eventId);
    }
}
