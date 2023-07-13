package ru.practicum.stats.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.entity.EndpointHit;
import ru.practicum.stats.entity.ViewStats;
import ru.practicum.stats.mapper.EndpointHitMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatsService {

    StatsRepository statsRepository;

    public void addEndpointHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = EndpointHitMapper.INSTANCE.toEndpointHit(endpointHitDto);
        statsRepository.addEndpointHit(endpointHit);
    }

    public List<ViewStats> getStats(String start, String end, String[] uris, boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);
        if (unique) {
            if (uris == null) return statsRepository.findViewStatsUniqueAllUris(startDateTime, endDateTime);
            return statsRepository.findViewStatsUnique(uris, startDateTime, endDateTime);
        }
        if (uris == null) return statsRepository.findViewStatsNotUniqueAllUris(startDateTime, endDateTime);
        return statsRepository.findViewStatsNotUnique(uris, startDateTime, endDateTime);
    }
}
