package ru.practicum.mainservice.model.request;

import java.util.List;

public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;

    public enum StatusEnum {CONFIRMED, REJECTED}
}
