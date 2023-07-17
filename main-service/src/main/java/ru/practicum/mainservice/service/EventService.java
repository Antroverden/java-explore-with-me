package ru.practicum.mainservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.mainservice.model.request.EventRequestStatusUpdateRequest;
import ru.practicum.mainservice.model.request.NewEventDto;
import ru.practicum.mainservice.model.request.UpdateEventAdminRequest;
import ru.practicum.mainservice.model.request.UpdateEventUserRequest;
import ru.practicum.mainservice.model.response.*;
import ru.practicum.mainservice.storage.CompilationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventService {

    CompilationRepository compilationRepository;


    public List<EventShortDto> getEvents(Integer userId, Integer from, Integer size) {

    }

    public CompilationDto addEvent(Integer userId, NewEventDto newEventDto) {

    }

    public List<EventFullDto> getEvent(Integer userId, Integer eventId) {

    }

    public EventFullDto changeEvent(Integer userId, Integer eventId, UpdateEventUserRequest updateEventUserRequest) {

    }

    public ParticipationRequestDto getEventRequests(Integer userId, Integer eventId) {

    }

    public EventRequestStatusUpdateResult changeEventRequests(
            Integer userId,
            Integer eventId,
            EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {

    }

    public List<EventFullDto> getEvents(
            Integer[] users,
            String[] states,
            Integer[] categories,
            String rangeStart,
            String rangeEnd,
            Integer from,
            Integer size
    ) {

    }

    public EventFullDto changeEvent(Integer eventId, UpdateEventAdminRequest updateEventAdminRequest) {

    }

    public List<EventShortDto> getEvents(
            String text,
            Integer[] categories,
            Boolean paid,
            String rangeStart,
            String rangeEnd,
            Boolean onlyAvailable,
            String sort,
            Integer from,
            Integer size
    ) {

    }

    public EventFullDto getEvent(Integer eventId) {

    }
}
