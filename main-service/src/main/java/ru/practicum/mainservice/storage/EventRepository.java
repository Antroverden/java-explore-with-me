package ru.practicum.mainservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
