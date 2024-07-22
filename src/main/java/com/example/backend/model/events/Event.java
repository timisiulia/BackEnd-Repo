package com.example.backend.model.events;

import com.example.backend.model.participants.Participant;
import com.example.backend.model.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder()
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Participant> paticipants;

    //vin pe request de aici in jos
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String description;

    private String location;

    private Boolean recurrency;

    private Integer recurrencyPeriod;



}
