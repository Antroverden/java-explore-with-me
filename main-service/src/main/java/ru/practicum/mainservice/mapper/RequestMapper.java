package ru.practicum.mainservice.mapper;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.mainservice.entity.Event;
import ru.practicum.mainservice.entity.ParticipationRequest;
import ru.practicum.mainservice.entity.User;
import ru.practicum.mainservice.exception.NotFoundException;
import ru.practicum.mainservice.model.response.ParticipationRequestDto;
import ru.practicum.mainservice.storage.EventRepository;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class RequestMapper {

    public static RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);
    private EventRepository eventRepository;

    public void setEventRepository(@Context EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public abstract ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest);
    Event map(Integer value){
        return eventRepository.findById(value).orElseThrow(NotFoundException::new);
    }

    Integer map(Event value){
        return value.getId();
    }

    Integer map(User value){
        return value.getId();
    }
    public abstract List<ParticipationRequestDto> toParticipationRequestDtos(List<ParticipationRequest> participationRequests);
}
