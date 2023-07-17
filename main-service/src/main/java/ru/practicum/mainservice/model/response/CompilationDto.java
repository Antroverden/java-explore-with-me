package ru.practicum.mainservice.model.response;

import java.util.List;

public class CompilationDto {

    private Long id;
    private Boolean pinned;
    private String title;
    private List<EventShortDto> events;
}
