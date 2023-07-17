package ru.practicum.mainservice.controller.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.model.response.CategoryDto;
import ru.practicum.mainservice.model.response.CompilationDto;
import ru.practicum.mainservice.service.CategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryService categoryService;

    @PostMapping
    public CategoryDto addCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @GetMapping("/{catId}")
    public CompilationDto deleteCategory(@PathVariable Integer catId) {
        return categoryService.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    public CompilationDto changeCategory(@PathVariable Integer catId, @RequestBody CategoryDto categoryDto) {
        return categoryService.changeCategory(catId, categoryDto);
    }
}
