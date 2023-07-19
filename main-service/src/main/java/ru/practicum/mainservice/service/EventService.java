package ru.practicum.mainservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.client.StatsClient;
import ru.practicum.mainservice.entity.Event;
import ru.practicum.mainservice.entity.Event.State;
import ru.practicum.mainservice.entity.User;
import ru.practicum.mainservice.exception.ConflictException;
import ru.practicum.mainservice.exception.ForbiddenException;
import ru.practicum.mainservice.exception.NotFoundException;
import ru.practicum.mainservice.mapper.EventMapper;
import ru.practicum.mainservice.model.request.EventRequestStatusUpdateRequest;
import ru.practicum.mainservice.model.request.NewEventDto;
import ru.practicum.mainservice.model.request.UpdateEventAdminRequest;
import ru.practicum.mainservice.model.request.UpdateEventUserRequest;
import ru.practicum.mainservice.model.response.EventFullDto;
import ru.practicum.mainservice.model.response.EventRequestStatusUpdateResult;
import ru.practicum.mainservice.model.response.EventShortDto;
import ru.practicum.mainservice.model.response.ParticipationRequestDto;
import ru.practicum.mainservice.storage.EventRepository;
import ru.practicum.mainservice.storage.UserRepository;
import ru.practicum.stats.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.practicum.mainservice.entity.Event.State.CANCELED;
import static ru.practicum.mainservice.entity.Event.State.PUBLISHED;
import static ru.practicum.mainservice.model.request.UpdateEventAdminRequest.StateAction.PUBLISH_EVENT;
import static ru.practicum.mainservice.model.request.UpdateEventAdminRequest.StateAction.REJECT_EVENT;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventService {

    EventRepository eventRepository;
    UserRepository userRepository;
    StatsClient statsClient;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<EventShortDto> getEvents(Integer userId, Integer from, Integer size) {
        List<Event> events = eventRepository.findAllByInitiator_Id(userId, PageRequest.of(from / size, size))
                .getContent();
        return EventMapper.INSTANCE.toEventDtos(events);
    }

    public EventFullDto addEvent(Integer userId, NewEventDto newEventDto) {
        LocalDateTime eventDateTime = LocalDateTime.parse(newEventDto.getEventDate(), formatter);
        if (LocalDateTime.now().plusHours(2).isAfter(eventDateTime)) {
            throw new ForbiddenException("Неправильно указана дата");
        }
        Event toSave = EventMapper.INSTANCE.toEvent(newEventDto);
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        toSave.setInitiator(user);
        Event savedEvent = eventRepository.save(toSave);
        return EventMapper.INSTANCE.toEventFullDto(savedEvent);
    }

    public EventFullDto getEvent(Integer userId, Integer eventId) {
        Event event = eventRepository.findByIdAndInitiator_Id(eventId, userId).orElseThrow(NotFoundException::new);
        return EventMapper.INSTANCE.toEventFullDto(event);
    }

    public EventFullDto changeEvent(Integer userId, Integer eventId, UpdateEventUserRequest updateEventUserRequest) {
        LocalDateTime eventDateTime = LocalDateTime.parse(updateEventUserRequest.getEventDate(), formatter);
        if (LocalDateTime.now().plusHours(2).isAfter(eventDateTime)) {
            throw new ForbiddenException("Неправильно указана дата");
        }
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        if (event.getState() == PUBLISHED) {
            throw new ConflictException("Only pending or canceled events can be changed");
        }
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("Данный пользователь не создавал событие");
        }
        Event toSave = EventMapper.INSTANCE.toEvent(updateEventUserRequest);
        Event savedEvent = eventRepository.save(toSave);
        return EventMapper.INSTANCE.toEventFullDto(savedEvent);
    }

    public ParticipationRequestDto getEventRequests(Integer userId, Integer eventId) {

    }

    public EventRequestStatusUpdateResult changeEventRequests(
            Integer userId,
            Integer eventId,
            EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {

    }

    public List<EventFullDto> getEvents(
            List<Integer> users,
            List<State> states,
            List<Integer> categories,
            String rangeStart,
            String rangeEnd,
            Integer from,
            Integer size
    ) {
        LocalDateTime start = LocalDateTime.parse(rangeStart, formatter);
        LocalDateTime end = LocalDateTime.parse(rangeEnd, formatter);
        List<Event> events = eventRepository
                .findAllByInitiator_IdInAndStateInAndCategory_IdInAndEventDateBeforeAndEventDateAfter(
                        users, states, categories, start, end, PageRequest.of(from / size, size))
                .getContent();
        return EventMapper.INSTANCE.toEventFullDtos(events);
    }

    public EventFullDto changeEvent(Integer eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        LocalDateTime eventDateTime = LocalDateTime.parse(updateEventAdminRequest.getEventDate(), formatter);
        if (LocalDateTime.now().plusHours(1).isAfter(eventDateTime)) {
            throw new ForbiddenException("Неправильно указана дата");
        }
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        if (updateEventAdminRequest.getStateAction() == PUBLISH_EVENT
                && (event.getState() == PUBLISHED || event.getState() == CANCELED)) {
            throw new ConflictException("Cannot publish published or canceled event");
        }
        if (updateEventAdminRequest.getStateAction() == REJECT_EVENT && event.getState() == PUBLISHED) {
            throw new ConflictException("Cannot reject published event");
        }
        Event toSave = EventMapper.INSTANCE.toEvent(updateEventAdminRequest);
        Event savedEvent = eventRepository.save(toSave);
        return EventMapper.INSTANCE.toEventFullDto(savedEvent);
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
            Integer size,
            String ip
    ) {
        List<Event> events;
        if (rangeStart == null && rangeEnd == null) {
            events = eventRepository.findAllByInitiator_Id(userId, PageRequest.of(from / size, size))
                    .getContent();
        } else {
            events = eventRepository.findAllByInitiator_Id(userId, PageRequest.of(from / size, size))
                    .getContent();
        }
        EndpointHitDto endpointHitDto = EndpointHitDto.builder().app("ewm-main-service").uri("/events/")
                .timestamp(LocalDateTime.now().toString()).ip(ip).build();
        statsClient.addHit(endpointHitDto);
        return EventMapper.INSTANCE.toEventDtos(events);
    }

    public EventFullDto getEvent(Integer eventId, String ip) {
        Event event = eventRepository.findByIdAndState(eventId, PUBLISHED).orElseThrow(NotFoundException::new);
        EndpointHitDto endpointHitDto = EndpointHitDto.builder().app("ewm-main-service").uri("/events/" + eventId)
                .timestamp(LocalDateTime.now().toString()).ip(ip).build();
        statsClient.addHit(endpointHitDto);
        return EventMapper.INSTANCE.toEventFullDto(event);
    }
}
