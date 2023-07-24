package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.mainservice.entity.Category;
import ru.practicum.mainservice.model.response.CategoryDto;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface CategoryMapper {

    Category toCategory(CategoryDto dto);

    CategoryDto toCategoryDto(Category category);

    List<CategoryDto> toCategoryDtos(List<Category> categories);
}
