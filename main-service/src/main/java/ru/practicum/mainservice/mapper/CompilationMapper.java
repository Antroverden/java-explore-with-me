package ru.practicum.mainservice.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.mainservice.entity.Compilation;
import ru.practicum.mainservice.entity.Event;
import ru.practicum.mainservice.model.request.NewCompilationDto;
import ru.practicum.mainservice.model.response.CompilationDto;
import ru.practicum.mainservice.storage.EventRepository;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CompilationMapper {

    public static CompilationMapper INSTANCE = Mappers.getMapper(CompilationMapper.class);
    private EventRepository eventRepository;

    public void setEventRepository(@Context EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public abstract Compilation toCompilation(NewCompilationDto dto);

    List<Event> map(List<Integer> values) {
        return eventRepository.findAllByIdIn(values);
    }

    public abstract CompilationDto toCompilationDto(Compilation compilation);

    public abstract List<CompilationDto> toCompilationDtos(List<Compilation> categories);
}
