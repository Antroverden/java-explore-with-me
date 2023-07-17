package ru.practicum.mainservice.model.request;

public class UpdateEventUserRequest {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;

    public enum StateActionEnum {SEND_TO_REVIEW, CANCEL_REVIEW}
}
