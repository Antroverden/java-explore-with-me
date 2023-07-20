package ru.practicum.mainservice.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    @NotBlank
    @Size(min = 20, max = 2000)
    String annotation;
    Integer category;
    @NotBlank
    @Size(min = 20, max = 7000)
    String description;
    String eventDate;
    LocationDto location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    @Size(min = 3, max = 120)
    String title;
}
