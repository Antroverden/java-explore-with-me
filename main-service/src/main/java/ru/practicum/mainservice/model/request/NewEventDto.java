package ru.practicum.mainservice.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewEventDto {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
}
