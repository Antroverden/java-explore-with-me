package ru.practicum.mainservice.mapper;

import org.mapstruct.*;
import ru.practicum.mainservice.entity.Compilation;
import ru.practicum.mainservice.model.request.NewCompilationDto;
import ru.practicum.mainservice.model.request.UpdateCompilationRequest;
import ru.practicum.mainservice.model.response.CompilationDto;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CompilationMapper {

    @Mapping(target = "events", ignore = true)
    public abstract Compilation toCompilation(NewCompilationDto dto);

    public abstract CompilationDto toCompilationDto(Compilation compilation);

    public abstract List<CompilationDto> toCompilationDtos(List<Compilation> categories);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "events", ignore = true)
    public abstract void updateCompilation(@MappingTarget Compilation compilation, UpdateCompilationRequest dto);
}
