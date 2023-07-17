package ru.practicum.mainservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}
