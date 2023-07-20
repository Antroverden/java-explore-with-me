package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.mainservice.entity.Event;
import ru.practicum.mainservice.entity.ParticipationRequest;
import ru.practicum.mainservice.entity.User;
import ru.practicum.mainservice.exception.NotFoundException;
import ru.practicum.mainservice.model.response.ParticipationRequestDto;
import ru.practicum.mainservice.storage.EventRepository;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public abstract class RequestMapper {

    private EventRepository eventRepository;

    @Autowired
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public abstract ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest);

    Event map(Integer value) {
        return eventRepository.findById(value).orElseThrow(NotFoundException::new);
    }

    Integer map(Event value) {
        return value.getId();
    }

    Integer map(User value) {
        return value.getId();
    }

    public abstract List<ParticipationRequestDto> toParticipationRequestDtos(List<ParticipationRequest> participationRequests);
}
