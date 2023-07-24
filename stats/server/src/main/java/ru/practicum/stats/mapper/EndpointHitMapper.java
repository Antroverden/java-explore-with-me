package ru.practicum.stats.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.model.EndpointHit;

@Mapper(componentModel = "spring")
@Component
public interface EndpointHitMapper {

    EndpointHit toEndpointHit(EndpointHitDto dto);
}
