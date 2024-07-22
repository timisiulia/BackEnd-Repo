package com.example.backend.controller;

import com.example.backend.model.participants.ParticipantsDetailsImpl;
import com.example.backend.model.request.participant.RespondEventRequest;
import com.example.backend.model.response.participant.RespondEventResponse;
import com.example.backend.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController()
@RequestMapping("/participant")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ParticipantController extends BaseApiController {
    private final ParticipantService participantService;

    @PostMapping("/respond")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ParticipantsDetailsImpl respondToEvent(final Principal principal,@RequestBody final RespondEventRequest payload) {
        return participantService.respondToEvent(resolveUserDetailsFromPrincipal(principal).getId(), payload);
    }
}
