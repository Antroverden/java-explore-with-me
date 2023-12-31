package ru.practicum.mainservice.controller.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.model.request.NewCompilationDto;
import ru.practicum.mainservice.model.request.UpdateCompilationRequest;
import ru.practicum.mainservice.model.response.CompilationDto;
import ru.practicum.mainservice.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminCompilationController {

    CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        return compilationService.addCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public String deleteCompilation(@PathVariable Integer compId) {
        compilationService.deleteCompilation(compId);
        return "Подборка удалена";
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(
            @PathVariable Integer compId,
            @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        return compilationService.updateCompilation(compId, updateCompilationRequest);
    }
}
