package com.example.backend.model.user;

import com.example.backend.model.events.Event;
import com.example.backend.model.participants.Participant;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@Builder()
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email", "username"}))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String password;

    private UserRole role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Event> eventsList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Participant> participants;


}
