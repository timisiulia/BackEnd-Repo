package com.example.backend.repository;

import com.example.backend.model.participants.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findParticipantsByUserId(final Long id);

    Participant findParticipantById(final Long id);

    @Query("SELECT p from Participant p where p.event.id = :eventId AND p.user.id = :userId")
    Participant findParticipantByEventIdAndUserId(@Param("eventId") final Long eventId, @Param("userId") final Long userId);
}
