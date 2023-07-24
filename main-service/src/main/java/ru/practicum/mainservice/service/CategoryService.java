package ru.practicum.mainservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.entity.Category;
import ru.practicum.mainservice.exception.ConflictException;
import ru.practicum.mainservice.exception.NotFoundException;
import ru.practicum.mainservice.mapper.CategoryMapper;
import ru.practicum.mainservice.model.response.CategoryDto;
import ru.practicum.mainservice.storage.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        try {
            Category savedCategory = categoryRepository.save(category);
            return categoryMapper.toCategoryDto(savedCategory);
        } catch (DataAccessException e) {
            throw new ConflictException("Название категории " + category.getName() + " уже существует");
        }
    }

    public void deleteCategory(Integer catId) {
        if (!categoryRepository.existsById(catId)) throw new NotFoundException("Категория не найдена");
        try {
            categoryRepository.deleteById(catId);
        } catch (DataAccessException e) {
            throw new ConflictException("Категория связана с другими");
        }
    }

    public CategoryDto changeCategory(Integer catId, CategoryDto categoryDto) {
        if (!categoryRepository.existsById(catId)) throw new NotFoundException("Категория не найдена");
        Category category = categoryMapper.toCategory(categoryDto);
        category.setId(catId);
        try {
            Category savedCategory = categoryRepository.save(category);
            return categoryMapper.toCategoryDto(savedCategory);
        } catch (DataAccessException e) {
            throw new ConflictException("Название категории " + category.getName() + " уже существует");
        }
    }

    public List<CategoryDto> getCategories(Integer from, Integer size) {
        List<Category> categories = categoryRepository.findAll(PageRequest.of(from / size, size)).getContent();
        return categoryMapper.toCategoryDtos(categories);
    }

    public CategoryDto getCategory(Integer catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(NotFoundException::new);
        return categoryMapper.toCategoryDto(category);
    }
}
