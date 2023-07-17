package ru.practicum.mainservice.model.request;

import java.util.List;

public class NewCompilationDto {
    private List<Long> events;
    private Boolean pinned;
    private String title;
}
