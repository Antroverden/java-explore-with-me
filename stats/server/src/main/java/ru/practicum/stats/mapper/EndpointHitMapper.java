package ru.practicum.stats.mapper;

import org.mapstruct.Mapper;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.model.EndpointHit;

@Mapper(componentModel = "spring")
public interface EndpointHitMapper {

    EndpointHit toEndpointHit(EndpointHitDto dto);
}
