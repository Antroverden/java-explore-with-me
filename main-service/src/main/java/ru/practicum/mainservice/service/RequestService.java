package ru.practicum.mainservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.model.response.ParticipationRequestDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestService {


    public List<ParticipationRequestDto> getRequests(Integer userId) {

    }

    public ParticipationRequestDto addRequest(Integer userId, Integer eventId) {

    }

    public ParticipationRequestDto cancelRequest(Integer userId, Integer requestId) {

    }
}
