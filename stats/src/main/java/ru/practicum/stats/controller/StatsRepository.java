package ru.practicum.stats.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.stats.entity.EndpointHit;

public interface StatsRepository extends JpaRepository<EndpointHit, Integer> {

}
