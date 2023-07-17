package ru.practicum.mainservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.model.response.CategoryDto;
import ru.practicum.mainservice.model.response.CompilationDto;
import ru.practicum.mainservice.storage.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {

    CategoryRepository categoryRepository;

    public CategoryDto addCategory(CategoryDto categoryDto) {

    }

    public CompilationDto deleteCategory(Integer catId) {

    }

    public CompilationDto changeCategory(Integer catId, CategoryDto categoryDto) {

    }

    public List<CategoryDto> getCategories(Integer from, Integer size) {

    }

    public CategoryDto getCategory(Integer catId) {

    }
}
