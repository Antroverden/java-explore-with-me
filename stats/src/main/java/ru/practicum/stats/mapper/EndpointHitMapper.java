package ru.practicum.stats.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.model.EndpointHit;

@Mapper
public interface EndpointHitMapper {
    EndpointHitMapper INSTANCE = Mappers.getMapper(EndpointHitMapper.class);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "app", source = "dto.app")
    @Mapping(target = "uri", source = "dto.uri")
    @Mapping(target = "ip", source = "dto.ip")
    @Mapping(target = "timestamp", source = "dto.timestamp", dateFormat = "yyyy-MM-dd HH:mm:ss")
    EndpointHit toEndpointHit(EndpointHitDto dto);
}
