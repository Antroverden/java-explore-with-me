package ru.practicum.mainservice.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UpdateCompilationRequest {
    private List<Long> events;
    private Boolean pinned;
    private String title;

}
