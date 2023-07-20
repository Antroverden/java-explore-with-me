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

    private CategoryRepository categoryRepository;

    @Autowired
    public void setEventRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    Category map(Integer value) {
        return categoryRepository.findById(value).orElseThrow(NotFoundException::new);
    }

    @Mapping(target = "eventDate", source = "dto.eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    public abstract Event toEvent(NewEventDto dto);

    @Mapping(target = "eventDate", source = "dto.eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    public abstract Event toEvent(UpdateEventUserRequest dto);

    @Mapping(target = "eventDate", source = "dto.eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    public abstract Event toEvent(UpdateEventAdminRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "eventDate", source = "dto.eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    public abstract void updateEvent(@MappingTarget Event entity, UpdateEventAdminRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "eventDate", source = "dto.eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    public abstract void updateEvent(@MappingTarget Event entity, UpdateEventUserRequest dto);

    public abstract EventFullDto toEventFullDto(Event event);

    public abstract List<EventShortDto> toEventDtos(List<Event> events);

    public abstract List<EventFullDto> toEventFullDtos(List<Event> events);
}
