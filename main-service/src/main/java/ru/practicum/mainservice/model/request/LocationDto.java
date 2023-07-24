package ru.practicum.mainservice.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    Double lat;
    Double lon;
}
