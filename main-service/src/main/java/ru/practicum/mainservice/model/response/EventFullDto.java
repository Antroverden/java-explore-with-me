package ru.practicum.mainservice.model.response;

import ru.practicum.mainservice.model.request.Location;

public class EventFullDto {
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private Long id;
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private String publishedOn;
    private Boolean requestModeration;

    public enum StateEnum {PENDING, PUBLISHED, CANCELED}
}
