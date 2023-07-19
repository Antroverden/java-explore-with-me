package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.mainservice.entity.Compilation;
import ru.practicum.mainservice.model.request.NewCompilationDto;
import ru.practicum.mainservice.model.response.CompilationDto;

import java.util.List;

@Mapper
public interface CompilationMapper {
    CompilationMapper INSTANCE = Mappers.getMapper(CompilationMapper.class);

    @Mapping(target = "pinned", source = "dto.pinned")
    @Mapping(target = "title", source = "dto.title")
    Compilation toCompilation(NewCompilationDto dto);

    CompilationDto toCompilationDto(Compilation compilation);

    List<CompilationDto> toCompilationDtos(List<Compilation> categories);
}
