package ru.practicum.mainservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.entity.Event;
import ru.practicum.mainservice.entity.Event.State;
import ru.practicum.mainservice.entity.ParticipationRequest;
import ru.practicum.mainservice.entity.User;
import ru.practicum.mainservice.exception.BadRequestException;
import ru.practicum.mainservice.exception.ConflictException;
import ru.practicum.mainservice.exception.ForbiddenException;
import ru.practicum.mainservice.exception.NotFoundException;
import ru.practicum.mainservice.mapper.EventMapper;
import ru.practicum.mainservice.mapper.RequestMapper;
import ru.practicum.mainservice.model.request.EventRequestStatusUpdateRequest;
import ru.practicum.mainservice.model.request.NewEventDto;
import ru.practicum.mainservice.model.request.UpdateEventAdminRequest;
import ru.practicum.mainservice.model.request.UpdateEventUserRequest;
import ru.practicum.mainservice.model.response.EventFullDto;
import ru.practicum.mainservice.model.response.EventRequestStatusUpdateResult;
import ru.practicum.mainservice.model.response.EventShortDto;
import ru.practicum.mainservice.model.response.ParticipationRequestDto;
import ru.practicum.mainservice.storage.EventRepository;
import ru.practicum.mainservice.storage.RequestRepository;
import ru.practicum.mainservice.storage.UserRepository;
import ru.practicum.stats.client.StatsClient;
import ru.practicum.stats.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.practicum.mainservice.entity.Event.State.PENDING;
import static ru.practicum.mainservice.entity.Event.State.PUBLISHED;
import static ru.practicum.mainservice.entity.ParticipationRequest.Status.CONFIRMED;
import static ru.practicum.mainservice.entity.ParticipationRequest.Status.REJECTED;
import static ru.practicum.mainservice.model.request.UpdateEventAdminRequest.StateAction.PUBLISH_EVENT;
import static ru.practicum.mainservice.model.request.UpdateEventAdminRequest.StateAction.REJECT_EVENT;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventService {

    EventRepository eventRepository;
    UserRepository userRepository;
    RequestRepository requestRepository;
    EventMapper eventMapper;
    RequestMapper requestMapper;
    StatsClient statsClient = new StatsClient();

    public List<EventShortDto> getEvents(Integer userId, Integer from, Integer size) {
        List<Event> events = eventRepository.findAllByInitiator_Id(userId, PageRequest.of(from / size, size))
                .getContent();
        return eventMapper.toEventDtos(events);
    }

    public EventFullDto addEvent(Integer userId, NewEventDto newEventDto) {
        if (LocalDateTime.now().plusHours(2).isAfter(newEventDto.getEventDate())) {
            throw new ForbiddenException("Неправильно указана дата");
        }
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Event toSave = eventMapper.toEvent(newEventDto);
        toSave.setInitiator(user);
        toSave.setCreatedOn(LocalDateTime.now());
        toSave.setState(PENDING);
        toSave.setConfirmedRequests(0);
        if (newEventDto.getPaid() == null) toSave.setPaid(false);
        if (newEventDto.getParticipantLimit() == null) toSave.setParticipantLimit(0);
        if (newEventDto.getRequestModeration() == null) toSave.setRequestModeration(true);
        eventRepository.save(toSave);
        return eventMapper.toEventFullDto(toSave);
    }

    public EventFullDto getEvent(Integer userId, Integer eventId) {
        Event event = eventRepository.findByIdAndInitiator_Id(eventId, userId).orElseThrow(NotFoundException::new);
        return eventMapper.toEventFullDto(event);
    }

    public EventFullDto changeEvent(Integer userId, Integer eventId, UpdateEventUserRequest updateEventUserRequest) {
        if (updateEventUserRequest.getEventDate() != null) {
            if (LocalDateTime.now().plusHours(2).isAfter(updateEventUserRequest.getEventDate())) {
                throw new ForbiddenException("Неправильно указана дата");
            }
        }
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        if (event.getState() == PUBLISHED) {
            throw new ConflictException("Only pending or canceled events can be changed");
        }
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("Данный пользователь не создавал событие");
        }
        eventMapper.updateEvent(event, updateEventUserRequest);
        if (updateEventUserRequest.getStateAction() == UpdateEventUserRequest.StateAction.SEND_TO_REVIEW)
            event.setState(PENDING);
        if (updateEventUserRequest.getStateAction() == UpdateEventUserRequest.StateAction.CANCEL_REVIEW)
            event.setState(State.CANCELED);
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toEventFullDto(savedEvent);
    }

    public List<ParticipationRequestDto> getEventRequests(Integer userId, Integer eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        List<ParticipationRequest> requests;
        if (event.getInitiator().getId().equals(userId)) {
            requests = requestRepository.findAllByEvent_Id(eventId);
        } else {
            requests = requestRepository.findAllByRequester_IdAndEvent_Id(userId, eventId);
        }
        return requestMapper.toParticipationRequestDtos(requests);
    }

    public EventRequestStatusUpdateResult changeEventRequests(
            Integer userId,
            Integer eventId,
            EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        Event event = eventRepository.findByIdAndInitiator_Id(eventId, userId).orElseThrow(NotFoundException::new);
        if (event.getConfirmedRequests() != null && event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            throw new BadRequestException("Подтверждение заявок не требуется");
        }
        if (requestRepository.existsParticipationRequestByIdInAndStatus(eventRequestStatusUpdateRequest.getRequestIds(), CONFIRMED)) {
            throw new ConflictException("Request must have status PENDING");
        }
        if (event.getConfirmedRequests() != null && event.getConfirmedRequests().equals(event.getParticipantLimit())) {
            throw new ConflictException("The participant limit has been reached");
        }
        List<ParticipationRequest> requests = requestRepository.findAllById(eventRequestStatusUpdateRequest.getRequestIds());
        EventRequestStatusUpdateResult eventRequestStatusUpdateResult = new EventRequestStatusUpdateResult();
        if (eventRequestStatusUpdateRequest.getStatus() == REJECTED) {
            requests.forEach(r -> r.setStatus(REJECTED));
            eventRequestStatusUpdateResult.setRejectedRequests(requestMapper.toParticipationRequestDtos(requests));
            event.setConfirmedRequests(event.getConfirmedRequests() - requests.size());
        } else {
            requests.forEach(r -> r.setStatus(CONFIRMED));
            eventRequestStatusUpdateResult.setConfirmedRequests(requestMapper.toParticipationRequestDtos(requests));
            event.setConfirmedRequests(event.getConfirmedRequests() + requests.size());
        }
        eventRepository.save(event);
        requestRepository.saveAll(requests);
        return eventRequestStatusUpdateResult;
    }

    public List<EventFullDto> getEvents(
            List<Integer> users,
            List<State> states,
            List<Integer> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Integer from,
            Integer size
    ) {
        List<Event> events;
        if (rangeStart == null && rangeEnd == null) {
            rangeStart = LocalDateTime.now();
            rangeEnd = rangeStart.plusYears(10);
        }
        if (rangeEnd.isBefore(rangeStart)) {
            throw new BadRequestException("Дата окончания не может быть до даты начала");
        }
        if (users != null && states != null && categories != null) {
            events = eventRepository
                    .findAllByInitiator_IdInAndStateInAndCategory_IdInAndEventDateBeforeAndEventDateAfter(
                            users, states, categories, rangeEnd, rangeStart, PageRequest.of(from / size, size))
                    .getContent();
        } else {
            events = eventRepository.findAllByEventDateBeforeAndEventDateAfter(rangeEnd, rangeStart,
                    PageRequest.of(from / size, size)).getContent();
        }
        return eventMapper.toEventFullDtos(events);
    }

    public EventFullDto changeEvent(Integer eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        if (updateEventAdminRequest != null) {
            if (updateEventAdminRequest.getEventDate() != null) {
                if (LocalDateTime.now().plusHours(1).isAfter(updateEventAdminRequest.getEventDate())) {
                    throw new ForbiddenException("Неправильно указана дата");
                }
            }
            Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
            if (updateEventAdminRequest.getStateAction() == PUBLISH_EVENT
                    && (event.getState() == PUBLISHED || event.getState() == State.CANCELED)) {
                throw new ConflictException("Cannot publish published or canceled event");
            }
            if (updateEventAdminRequest.getStateAction() == REJECT_EVENT && event.getState() == PUBLISHED) {
                throw new ConflictException("Cannot reject published event");
            }
            eventMapper.updateEvent(event, updateEventAdminRequest);
            if (updateEventAdminRequest.getStateAction() == PUBLISH_EVENT) event.setState(PUBLISHED);
            if (updateEventAdminRequest.getStateAction() == REJECT_EVENT) event.setState(State.CANCELED);
            Event savedEvent = eventRepository.save(event);
            return eventMapper.toEventFullDto(savedEvent);
        }
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        if (event.getState() == PUBLISHED) throw new ConflictException("Cannot publish published or canceled event");
        return eventMapper.toEventFullDto(event);
    }

    public List<EventShortDto> getEvents(
            String text,
            List<Integer> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            String sort,
            Integer from,
            Integer size,
            String ip
    ) {
        List<Event> events;
        if (rangeStart == null && rangeEnd == null) {
            rangeStart = LocalDateTime.now();
            rangeEnd = rangeStart.plusYears(10);
        }
        if (rangeEnd.isBefore(rangeStart)) {
            throw new BadRequestException("Дата окончания не может быть до даты начала");
        }
        if (text != null && paid != null && sort != null && categories != null) {
            events = eventRepository.findAllByAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndCategory_IdInAndPaidIsAndEventDateBeforeAndEventDateAfter(
                            text, text, categories, paid, rangeEnd, rangeStart, PageRequest.of(from / size, size))
                    .getContent();
        } else {
            events = eventRepository.findAllByEventDateBeforeAndEventDateAfter(rangeEnd, rangeStart,
                    PageRequest.of(from / size, size)).getContent();
        }
        EndpointHitDto endpointHitDto = EndpointHitDto.builder().app("ewm-main-service").uri("/events")
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)).ip(ip).build();
        statsClient.addHit(endpointHitDto);
        return eventMapper.toEventShortDtos(events);
    }

    public EventFullDto getEvent(Integer eventId, String ip) {
        Event event = eventRepository.findByIdAndStateIn(eventId, List.of(PUBLISHED)).orElseThrow(NotFoundException::new);
        EndpointHitDto endpointHitDto = EndpointHitDto.builder().app("ewm-main-service").uri("/events/" + eventId)
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)).ip(ip).build();
        if (event.getViews() == null) event.setViews(1L);
        else event.setViews(event.getViews() + 1);
        statsClient.addHit(endpointHitDto);
        return eventMapper.toEventFullDto(event);
    }
}
