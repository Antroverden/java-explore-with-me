package ru.practicum.mainservice.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompilationRequest {
    List<Integer> events;
    Boolean pinned;
    String title;

}
