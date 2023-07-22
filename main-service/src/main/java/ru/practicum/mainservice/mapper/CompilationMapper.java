package ru.practicum.mainservice.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.mainservice.entity.Compilation;
import ru.practicum.mainservice.entity.Event;
import ru.practicum.mainservice.model.request.NewCompilationDto;
import ru.practicum.mainservice.model.request.UpdateCompilationRequest;
import ru.practicum.mainservice.model.response.CompilationDto;
import ru.practicum.mainservice.storage.EventRepository;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public abstract class CompilationMapper {

    private EventRepository eventRepository;

    @Autowired
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public abstract Compilation toCompilation(NewCompilationDto dto);

    List<Event> map(List<Integer> values) {
        if (values != null) return eventRepository.findAllByIdIn(values);
        return null;
    }

    public abstract CompilationDto toCompilationDto(Compilation compilation);

    public abstract List<CompilationDto> toCompilationDtos(List<Compilation> categories);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateCompilation(@MappingTarget Compilation compilation, UpdateCompilationRequest dto);
}
