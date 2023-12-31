package ru.practicum.stats.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatsController {

    StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public String addEndpointHit(@RequestBody EndpointHitDto endpointHitDto) {
        statsService.addEndpointHit(endpointHitDto);
        return "Информация сохранена";
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(required = false) String[] uris,
            @RequestParam(required = false) boolean unique) {
        return statsService.getStats(start, end, uris, unique);
    }
}
