package ru.practicum.mainservice.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.mainservice.entity.Event;
import ru.practicum.mainservice.model.request.LocationDto;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    String annotation;
    CategoryDto category;
    Long confirmedRequests;
    String createdOn;
    String description;
    String eventDate;
    Integer id;
    UserShortDto initiator;
    LocationDto location;
    Boolean paid;
    Integer participantLimit;
    String publishedOn;
    Boolean requestModeration;
    Event.State state;
    String title;
    Long views;
}
