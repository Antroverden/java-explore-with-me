package ru.practicum.mainservice.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.mainservice.model.request.Location;

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
    Long id;
    UserShortDto initiator;
    Location location;
    Boolean paid;
    Integer participantLimit;
    String publishedOn;
    Boolean requestModeration;

    public enum StateEnum {PENDING, PUBLISHED, CANCELED}
}
