package ru.practicum.stats.client;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.stats.dto.EndpointHitDto;

public class StatsClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public void addHit(EndpointHitDto endpointHitDto) {
        HttpEntity<EndpointHitDto> request = new HttpEntity<>(endpointHitDto);
        restTemplate.postForObject("http://stats-server:9090/hit", request, EndpointHitDto.class);
    }
}
