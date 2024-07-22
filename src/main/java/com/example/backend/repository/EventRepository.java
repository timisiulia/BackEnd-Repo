package com.example.backend.repository;

import com.example.backend.model.events.Event;
import com.example.backend.model.events.EventIntersection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
//    @Query("SELECT events from Event events where events.user.id = 5")

    @Query(value = "SELECT user_id AS user,ARRAY_AGG(start_date||','||end_date)AS interval FROM ((SELECT e.user_id,e.start_date,e.end_date FROM events e JOIN users u ON e.user_id=u.id WHERE e.user_id IN(?1)AND e.start_date BETWEEN ?2 AND ?3 AND e.end_date BETWEEN ?2 AND ?3)UNION ALL(SELECT p.user_id,e.start_date,e.end_date FROM participants_events p JOIN events e ON p.event_id=e.id WHERE p.user_id IN(?1)AND e.start_date BETWEEN ?2 AND ?3 AND e.end_date BETWEEN ?2 AND ?3 ))subquery GROUP BY user_id", nativeQuery = true)
    List<EventIntersection> findEventsIntersectionForUsers(final List<Long> userIds, final LocalDateTime start, final LocalDateTime end);
    List<Event> findEventsByUserId(final Long id);
    Optional<Event> findById(final Long id);
}
