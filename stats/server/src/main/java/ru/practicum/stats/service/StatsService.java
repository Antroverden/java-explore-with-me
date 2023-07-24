package ru.practicum.stats.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.mapper.EndpointHitMapper;
import ru.practicum.stats.mapper.ViewStatsMapper;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.storage.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatsService {

    StatsRepository statsRepository;
    EndpointHitMapper endpointHitMapper;
    ViewStatsMapper viewStatsMapper;

    public void addEndpointHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = endpointHitMapper.toEndpointHit(endpointHitDto);
        statsRepository.addEndpointHit(endpointHit);
    }

    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        if (end.isBefore(start)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Дата окончания не может быть до даты начала");
        }
        if (unique) {
            if (uris == null) {
                return viewStatsMapper.toViewStatsDto(statsRepository
                        .findViewStatsUniqueAllUris(start, end));
            }
            return viewStatsMapper.toViewStatsDto(statsRepository
                    .findViewStatsUnique(uris, start, end));
        }
        if (uris == null) {
            return viewStatsMapper.toViewStatsDto(statsRepository
                    .findViewStatsNotUniqueAllUris(start, end));
        }
        return viewStatsMapper.toViewStatsDto(statsRepository
                .findViewStatsNotUnique(uris, start, end));
    }
}
