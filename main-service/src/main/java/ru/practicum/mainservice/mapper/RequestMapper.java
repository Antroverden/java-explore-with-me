package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.mainservice.entity.ParticipationRequest;
import ru.practicum.mainservice.model.response.ParticipationRequestDto;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class RequestMapper {

    @Mapping(target = "event", ignore = true)
    @Mapping(target = "requester", ignore = true)
    public abstract ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest);

    public abstract List<ParticipationRequestDto> toParticipationRequestDtos(
            List<ParticipationRequest> participationRequests);
}
