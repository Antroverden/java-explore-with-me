package ru.practicum.mainservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.model.request.NewCompilationDto;
import ru.practicum.mainservice.model.request.UpdateCompilationRequest;
import ru.practicum.mainservice.model.response.CompilationDto;
import ru.practicum.mainservice.storage.CompilationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompilationService {

    CompilationRepository compilationRepository;


    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {

    }

    public CompilationDto getCompilation(Integer compId) {

    }

    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {

    }

    public void deleteCompilation(Integer compId) {

    }

    public CompilationDto updateCompilation(
            Integer compId,
            UpdateCompilationRequest updateCompilationRequest) {

    }
}
