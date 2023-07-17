package ru.practicum.mainservice.controller.all;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.model.response.CompilationDto;
import ru.practicum.mainservice.service.CompilationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("compilations")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompilationController {

    CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getCompilations(
            @RequestParam Boolean pinned,
            @RequestParam Integer from,
            @RequestParam Integer size
    ) {
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilation(@PathVariable Integer compId) {
        return compilationService.getCompilation(compId);
    }
}
