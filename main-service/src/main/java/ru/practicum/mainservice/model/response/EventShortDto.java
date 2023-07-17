package ru.practicum.mainservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventShortDto {
    private String annotation;
    
    private CategoryDto category;
    
    private Long confirmedRequests;
    
    private String eventDate;
    
    private Long id;
    
    private UserShortDto initiator;
    
    private Boolean paid;
    
    private String title;
    
    private Long views;
}
