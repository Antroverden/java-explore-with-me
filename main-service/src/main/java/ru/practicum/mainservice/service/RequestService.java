package ru.practicum.mainservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.entity.Event;
import ru.practicum.mainservice.entity.ParticipationRequest;
import ru.practicum.mainservice.entity.User;
import ru.practicum.mainservice.exception.ConflictException;
import ru.practicum.mainservice.exception.NotFoundException;
import ru.practicum.mainservice.mapper.RequestMapper;
import ru.practicum.mainservice.model.response.ParticipationRequestDto;
import ru.practicum.mainservice.storage.EventRepository;
import ru.practicum.mainservice.storage.RequestRepository;
import ru.practicum.mainservice.storage.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.practicum.mainservice.entity.ParticipationRequest.Status.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestService {

    RequestRepository requestRepository;
    UserRepository userRepository;
    EventRepository eventRepository;
    RequestMapper requestMapper;

    public List<ParticipationRequestDto> getRequests(Integer userId) {
        if (!userRepository.existsById(userId)) throw new NotFoundException("Пользователь не найден");
        List<ParticipationRequest> requests = requestRepository.findAllByRequester_Id(userId);
        return requestMapper.toParticipationRequestDtos(requests);
    }

    public ParticipationRequestDto addRequest(Integer userId, Integer eventId) {
        if (requestRepository.existsParticipationRequestByRequester_idAndEvent_Id(userId, eventId)) {
            throw new ConflictException("Запрос уже существует");
        }
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        if (event.getParticipantLimit() != 0 && event.getConfirmedRequests() != null && event.getConfirmedRequests().equals(event.getParticipantLimit())) {
            throw new ConflictException("The participant limit has been reached");
        }
        if (event.getInitiator().getId().equals(userId) || event.getState() == Event.State.PENDING
                || event.getState() == Event.State.CANCELED || (event.getConfirmedRequests() > 0 && event.getConfirmedRequests().equals(event.getParticipantLimit()))) {
            throw new ConflictException("Нельзя участвовать в своем или неопубликованном событии");
        }
        User requester = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        ParticipationRequest participationRequest = ParticipationRequest.builder()
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)).requester(requester).event(event).build();
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            participationRequest.setStatus((CONFIRMED));
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        } else participationRequest.setStatus((PENDING));
        ParticipationRequest saved = requestRepository.save(participationRequest);
        return requestMapper.toParticipationRequestDto(saved);
    }

    //
    public ParticipationRequestDto cancelRequest(Integer userId, Integer requestId) {
        ParticipationRequest participationRequest = requestRepository
                .findById(requestId).orElseThrow(() -> new NotFoundException("Запрос не существует"));
        if (!participationRequest.getRequester().getId().equals(userId)) {
            throw new NotFoundException("Пользователь не явлется владельцем запроса");
        }
        participationRequest.setStatus(CANCELED);
        requestRepository.save(participationRequest);
        return requestMapper.toParticipationRequestDto(participationRequest);
    }
}
