package ru.practicum.stats.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.model.ViewStats;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ViewStatsMapper {

    List<ViewStatsDto> toViewStatsDto(List<ViewStats> viewStats);
}
