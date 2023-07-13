package ru.practicum.stats.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.entity.EndpointHit;
import ru.practicum.stats.entity.ViewStats;
import ru.practicum.stats.mapper.EndpointHitMapper;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatsService {

    StatsRepository statsRepository;

    public void addEndpointHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = EndpointHitMapper.INSTANCE.toEndpointHit(endpointHitDto);
        statsRepository.save(endpointHit);
    }

    public ViewStats getStats(String start, String end, String[] uris, Boolean unique) {
        if (unique) {
            return null;
        }
        return null;
    }
}
