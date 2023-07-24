package ru.practicum.stats.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ViewStats {
    String app;
    String uri;
    Integer hits;
}
