package com.example.backend.controller;

import com.example.backend.model.events.Event;
import com.example.backend.model.events.EventDetailsImpl;
import com.example.backend.model.events.EventIntersection;
import com.example.backend.model.request.event.CreateEventRequest;
import com.example.backend.model.request.event.IntersectionEventRequest;
import com.example.backend.model.request.event.UpdateEventRequest;
import com.example.backend.model.response.RegisterResponse;
import com.example.backend.model.response.event.CreateEventResponse;
import com.example.backend.model.response.event.UpdateEventResponse;
import com.example.backend.model.user.UserDetailsImpl;
import com.example.backend.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.example.backend.utils.EventMapper.formEvent;

@RestController()
@RequestMapping("/event")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EventController extends BaseApiController {
    private final EventService eventService;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<CreateEventResponse> getEvents(final Principal principal) {
        return eventService.getEvents(resolveUserDetailsFromPrincipal(principal));
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public CreateEventResponse createEvent(final Principal principal, @RequestBody final CreateEventRequest event) {
        return eventService.createNewEvent(resolveUserDetailsFromPrincipal(principal), event);
    }

    @PostMapping("/concurrency")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<EventIntersection> getIntersection(final Principal principal, @RequestBody final IntersectionEventRequest payload) {
        return eventService.getIntersection(resolveUserDetailsFromPrincipal(principal), payload);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public UpdateEventResponse updateEventResponse(@RequestBody final UpdateEventRequest event, @PathVariable("id") Long eventId) {
        return UpdateEventResponse.builder()
                .eventDetails(formEvent(eventService.updateEvent(event, eventId)))
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Void> deteleEvent(@PathVariable("id") Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok().build();

    }

}
