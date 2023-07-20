package ru.practicum.stats.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHitDto {
    Integer id;
    String app;
    String uri;
    String ip;
    String timestamp;
}
