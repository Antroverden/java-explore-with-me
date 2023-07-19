package ru.practicum.mainservice.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Page<Event> findAllByInitiator_Id(Integer userId, Pageable pageable);
    Page<Event> findAllByInitiator_IdIn(List<Integer> userId, Pageable pageable);

    Optional<Event> findByIdAndInitiator_Id(Integer eventId, Integer userId);
    Optional<Event> findByIdAndState(Integer eventId, Event.State state);
}
