package ru.practicum.mainservice.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.entity.Event;
import ru.practicum.mainservice.entity.Event.State;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    Page<Event> findAllByInitiator_Id(Integer userId, Pageable pageable);
    List<Event> findAllByIdIn(List<Integer> eventIds);
    Page<Event> findAllByInitiator_IdIn(List<Integer> userId, Pageable pageable);
    Page<Event> findAllByInitiator_IdInAndStateInAndCategory_IdInAndEventDateBeforeAndEventDateAfter(
            List<Integer> userId, List<State> states, List<Integer> categoryId, LocalDateTime start,
            LocalDateTime end, Pageable pageable);

    Optional<Event> findByIdAndInitiator_Id(Integer eventId, Integer userId);
    Optional<Event> findByIdAndState(Integer eventId, State state);
}
