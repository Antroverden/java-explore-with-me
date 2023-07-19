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
import java.util.List;

import static ru.practicum.mainservice.entity.Event.State.PENDING;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestService {

    RequestRepository requestRepository;
    UserRepository userRepository;
    EventRepository eventRepository;

    public List<ParticipationRequestDto> getRequests(Integer userId) {
        if (!userRepository.existsById(userId)) throw new NotFoundException("Пользователь не найден");
        List<ParticipationRequest> requests = requestRepository.findAllByRequester_Id(userId);
        return RequestMapper.INSTANCE.toParticipationRequestDtos(requests);
    }

    public ParticipationRequestDto addRequest(Integer userId, Integer eventId) {
        if (requestRepository.existsParticipationRequestByRequester_idAndEvent_Id(userId, eventId)) {
            throw new ConflictException("Запрос уже существует");
        }
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        if (event.getInitiator().getId().equals(userId) || event.getState() == PENDING
                || event.getState() == Event.State.CANCELED) {
            throw new ConflictException("Нельзя участвовать в своем или неопубликованном событии");
        }
        User requester = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        ParticipationRequest participationRequest = ParticipationRequest.builder().created(LocalDateTime.now())
                .requester(requester).event(event).status(ParticipationRequest.Status.PENDING).build();
        if (!event.getRequestModeration()) participationRequest.setStatus(ParticipationRequest.Status.APPROVED);
        ParticipationRequest saved = requestRepository.save(participationRequest);
        return RequestMapper.INSTANCE.toParticipationRequestDto(saved);
    }

    //        || (event.getConfirmedRequests() > 0 && event.getConfirmedRequests().equals(event.getParticipantLimit())
    public ParticipationRequestDto cancelRequest(Integer userId, Integer requestId) {
        ParticipationRequest participationRequest = requestRepository
                .findById(requestId).orElseThrow(() -> new NotFoundException("Запрос не существует"));
        if (!participationRequest.getRequester().getId().equals(userId)) {
            throw new NotFoundException("Пользователь не явлется владельцем запроса");
        }
        participationRequest.setStatus(ParticipationRequest.Status.CANCELED);
        requestRepository.save(participationRequest);
        return RequestMapper.INSTANCE.toParticipationRequestDto(participationRequest);
    }
}
