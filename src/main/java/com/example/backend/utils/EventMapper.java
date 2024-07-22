package com.example.backend.utils;

import com.example.backend.model.events.Event;
import com.example.backend.model.events.EventDetailsImpl;
import com.example.backend.model.participants.ParticipantsDetailsImpl;
import com.example.backend.model.response.event.CreateEventResponse;

public class EventMapper {
    public static Event toEvent (final EventDetailsImpl eventDetails){
        return Event.builder()
                .id(eventDetails.getId())
                .startDate(eventDetails.getStartDate())
                .endDate(eventDetails.getEndDate())
                .description(eventDetails.getDescription())
                .location(eventDetails.getLocation())
                .recurrency(eventDetails.getRecurrency())
                .recurrencyPeriod(eventDetails.getRecurrencyPeriod())
                .build();
    }

    public static CreateEventResponse toResponseEvent(final Event event) {
        return CreateEventResponse.builder()
                .id(event.getId())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .description(event.getDescription())
                .location(event.getLocation())
                .recurrency(event.getRecurrency())
                .recurrencyPeriod(event.getRecurrencyPeriod())
                .user(event.getUser().getId())
                .participants(event.getPaticipants().stream().map(ParticipantsDetailsImpl::build).toList())
                .build();
    }
    public static EventDetailsImpl formEvent(final Event event){
        return EventDetailsImpl.builder()
                .id(event.getId())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .description(event.getDescription())
                .location(event.getLocation())
                .recurrency(event.getRecurrency())
                .recurrencyPeriod(event.getRecurrencyPeriod())
                .userId(event.getUser().getId())
                .build();
    }
}
