package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.mainservice.entity.ParticipationRequest;
import ru.practicum.mainservice.model.response.ParticipationRequestDto;

import java.util.List;

@Mapper
public interface RequestMapper {
    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);

    ParticipationRequest toParticipationRequest(ParticipationRequestDto dto);

    ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest);

    List<ParticipationRequestDto> toParticipationRequestDtos(List<ParticipationRequest> participationRequests);
}
