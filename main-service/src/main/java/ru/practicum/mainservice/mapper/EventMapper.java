package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.mainservice.entity.Event;
import ru.practicum.mainservice.model.request.NewEventDto;
import ru.practicum.mainservice.model.request.UpdateEventAdminRequest;
import ru.practicum.mainservice.model.request.UpdateEventUserRequest;
import ru.practicum.mainservice.model.response.EventFullDto;
import ru.practicum.mainservice.model.response.EventShortDto;

import java.util.List;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    Event toEvent(NewEventDto dto);
    Event toEvent(UpdateEventUserRequest updateEventUserRequest);
    Event toEvent(UpdateEventAdminRequest updateEventAdminRequest);
    EventFullDto toEventFullDto(Event event);
    List<EventShortDto> toEventDtos(List<Event> events);
    List<EventFullDto> toEventFullDtos(List<Event> events);
}
