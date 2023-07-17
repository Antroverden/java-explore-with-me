package ru.practicum.mainservice.model.request;

public class UpdateEventAdminRequest {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;

    public enum StateActionEnum {PUBLISH_EVENT, REJECT_EVENT}
}
