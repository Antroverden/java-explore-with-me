package ru.practicum.stats.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.model.ViewStats;

import java.util.List;

@Mapper
public interface ViewStatsMapper {

    ViewStatsMapper INSTANCE = Mappers.getMapper(ViewStatsMapper.class);
    List<ViewStatsDto> toViewStatsDto(List<ViewStats> viewStats);
}
