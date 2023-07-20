package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
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

@Mapper(componentModel = "spring", uses = CategoryRepository.class)
public abstract class EventMapper {

    public static EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);
    private CategoryRepository categoryRepository;

    public abstract Event toEvent(NewEventDto dto);

    Category map(Integer value) {
        return categoryRepository.findById(value).orElseThrow(NotFoundException::new);
    }

    public abstract Event toEvent(UpdateEventUserRequest updateEventUserRequest);

    public abstract Event toEvent(UpdateEventAdminRequest updateEventAdminRequest);

    public abstract EventFullDto toEventFullDto(Event event);

    public abstract List<EventShortDto> toEventDtos(List<Event> events);

    public abstract List<EventFullDto> toEventFullDtos(List<Event> events);
}
