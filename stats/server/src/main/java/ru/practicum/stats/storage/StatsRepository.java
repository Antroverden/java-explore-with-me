package ru.practicum.stats.storage;

import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository {

    void addEndpointHit(EndpointHit endpointHit);

    List<ViewStats> findViewStatsNotUniqueAllUris(LocalDateTime start, LocalDateTime end);

    List<ViewStats> findViewStatsUniqueAllUris(LocalDateTime start, LocalDateTime end);

    List<ViewStats> findViewStatsNotUnique(String[] uris, LocalDateTime start, LocalDateTime end);

    List<ViewStats> findViewStatsUnique(String[] uris, LocalDateTime start, LocalDateTime end);
}
