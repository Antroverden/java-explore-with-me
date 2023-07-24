package ru.practicum.mainservice.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.mainservice.entity.Category;
import ru.practicum.mainservice.entity.Event;
import ru.practicum.mainservice.exception.NotFoundException;
import ru.practicum.mainservice.model.request.NewEventDto;
import ru.practicum.mainservice.model.request.UpdateEventAdminRequest;
import ru.practicum.mainservice.model.request.UpdateEventUserRequest;
import ru.practicum.mainservice.model.response.EventFullDto;
import ru.practicum.mainservice.model.response.EventShortDto;
import ru.practicum.mainservice.storage.CategoryRepository;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public abstract class EventMapper {

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "eventDate", source = "dto.eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    public abstract Event toEvent(NewEventDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "eventDate", source = "dto.eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    public abstract void updateEvent(@MappingTarget Event entity, UpdateEventAdminRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "eventDate", source = "dto.eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    public abstract void updateEvent(@MappingTarget Event entity, UpdateEventUserRequest dto);

    public abstract EventFullDto toEventFullDto(Event event);

    public abstract List<EventShortDto> toEventDtos(List<Event> events);

    public abstract List<EventFullDto> toEventFullDtos(List<Event> events);

    public abstract List<EventShortDto> toEventShortDtos(List<Event> events);
}
