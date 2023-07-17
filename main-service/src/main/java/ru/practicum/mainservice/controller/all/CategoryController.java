package ru.practicum.mainservice.controller.all;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.model.response.CategoryDto;
import ru.practicum.mainservice.model.response.CompilationDto;
import ru.practicum.mainservice.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryService categoryService;
    @GetMapping
    public List<CategoryDto> getCategories(
            @RequestParam Integer from,
            @RequestParam Integer size
    ) {
        return categoryService.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable Integer catId) {
        return categoryService.getCategory(catId);
    }
}
