package ru.practicum.stats.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ViewStatsDto {
    String app;
    String uri;
    Integer hits;
}
