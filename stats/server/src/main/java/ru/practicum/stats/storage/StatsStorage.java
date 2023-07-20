package ru.practicum.stats.storage;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.model.ViewStats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Primary
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatsStorage implements StatsRepository {

    JdbcTemplate jdbcTemplate;

    @Override
    public void addEndpointHit(EndpointHit endpointHit) {
        String sqlQuery = "insert into endpoint_hit(app, uri, ip, timestamp) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery, endpointHit.getApp(), endpointHit.getUri(), endpointHit.getIp(),
                endpointHit.getTimestamp());
    }

    @Override
    public List<ViewStats> findViewStatsNotUniqueAllUris(LocalDateTime start, LocalDateTime end) {
        String sqlQuery = "SELECT APP, URI, count(*) as hits FROM endpoint_hit " +
                "WHERE timestamp between ? and ? group by APP, URI order by hits desc";
        return jdbcTemplate.query(sqlQuery, this::mapRowToViewStats, start, end);
    }

    @Override
    public List<ViewStats> findViewStatsUniqueAllUris(LocalDateTime start, LocalDateTime end) {
        String sqlQuery = "SELECT APP, URI, count(distinct ip) as hits FROM endpoint_hit " +
                "WHERE timestamp between ? and ? group by APP, URI, IP order by hits desc";
        return jdbcTemplate.query(sqlQuery, this::mapRowToViewStats, start, end);
    }

    @Override
    public List<ViewStats> findViewStatsNotUnique(String[] uris, LocalDateTime start, LocalDateTime end) {
        String sqlQuery = "SELECT APP, URI, count(*) as hits FROM endpoint_hit " +
                "WHERE uri = any (?) and timestamp between ? and ? group by APP, URI order by hits desc";
        return jdbcTemplate.query(sqlQuery, this::mapRowToViewStats, uris, start, end);
    }

    @Override
    public List<ViewStats> findViewStatsUnique(String[] uris, LocalDateTime start, LocalDateTime end) {
        String sqlQuery = "SELECT APP, URI, count(distinct ip) as hits FROM endpoint_hit " +
                "WHERE uri = any (?) and timestamp between ? and ? group by APP, URI order by hits desc";
        return jdbcTemplate.query(sqlQuery, this::mapRowToViewStats, uris, start, end);
    }

    private ViewStats mapRowToViewStats(ResultSet resultSet, int rowNum) throws SQLException {
        return ViewStats.builder()
                .app(resultSet.getString("app"))
                .uri(resultSet.getString("uri"))
                .hits(resultSet.getInt("hits"))
                .build();
    }
}
