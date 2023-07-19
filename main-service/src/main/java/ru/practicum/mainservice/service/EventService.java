package ru.practicum.mainservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.entity.Event;
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

    public List<EventShortDto> getEvents(Integer userId, Integer from, Integer size) {
        List<Event> events = eventRepository.findAllByInitiator_Id(userId, PageRequest.of(from / size, size))
                .getContent();
        return EventMapper.INSTANCE.toEventDtos(events);
    }

    public EventFullDto addEvent(Integer userId, NewEventDto newEventDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
            String[] states,
            Integer[] categories,
            String rangeStart,
            String rangeEnd,
            Integer from,
            Integer size
    ) {
        List<Event> events = eventRepository.findAllByInitiator_IdIn(users, PageRequest.of(from / size, size))
                .getContent();
        return EventMapper.INSTANCE.toEventFullDtos(events);
    }

    public EventFullDto changeEvent(Integer eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
            Integer size
    ) {

    }

    public EventFullDto getEvent(Integer eventId) {
        Event event = eventRepository.findByIdAndState(eventId, PUBLISHED).orElseThrow(NotFoundException::new);

        return EventMapper.INSTANCE.toEventFullDto(event);
    }
}
