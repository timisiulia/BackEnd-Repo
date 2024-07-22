package com.example.backend.model.events;


import com.example.backend.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@Builder
public class EventDetailsImpl {
    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String description;

    private String location;

    private Boolean recurrency;

    private Integer recurrencyPeriod;

    private Long userId;
    public static EventDetailsImpl build(final Event event){
        return builder()
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
