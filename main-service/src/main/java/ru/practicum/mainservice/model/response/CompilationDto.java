package ru.practicum.mainservice.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    Integer id;
    Boolean pinned;
    String title;
    List<EventShortDto> events;
}
