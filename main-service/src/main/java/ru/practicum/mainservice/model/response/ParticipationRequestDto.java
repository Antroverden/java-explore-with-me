package ru.practicum.mainservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParticipationRequestDto {
    private String created;
    private Long event;
    private Long id;
    private Long requester;
    private String status;
}
