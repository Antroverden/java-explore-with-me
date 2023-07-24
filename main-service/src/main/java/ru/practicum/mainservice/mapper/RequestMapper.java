package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.mainservice.entity.Event;
import ru.practicum.mainservice.entity.ParticipationRequest;
import ru.practicum.mainservice.entity.User;
import ru.practicum.mainservice.model.response.ParticipationRequestDto;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public abstract class RequestMapper {

    public abstract ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest);

    Integer map(Event value) {
        return value.getId();
    }

    Integer map(User value) {
        return value.getId();
    }

    public abstract List<ParticipationRequestDto> toParticipationRequestDtos(List<ParticipationRequest> participationRequests);
}
