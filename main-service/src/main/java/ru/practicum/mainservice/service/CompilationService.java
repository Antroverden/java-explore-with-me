package ru.practicum.mainservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.entity.Compilation;
import ru.practicum.mainservice.entity.Event;
import ru.practicum.mainservice.exception.NotFoundException;
import ru.practicum.mainservice.mapper.CompilationMapper;
import ru.practicum.mainservice.model.request.NewCompilationDto;
import ru.practicum.mainservice.model.request.UpdateCompilationRequest;
import ru.practicum.mainservice.model.response.CompilationDto;
import ru.practicum.mainservice.storage.CompilationRepository;
import ru.practicum.mainservice.storage.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompilationService {

    CompilationRepository compilationRepository;
    EventRepository eventRepository;


    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        List<Compilation> compilations;
        if (!pinned) {
            compilations = compilationRepository.findAll(PageRequest.of(from / size, size))
                    .getContent();
        } else {
            compilations = compilationRepository
                    .findCompilationsByPinnedTrue(PageRequest.of(from / size, size)).getContent();
        }
        return CompilationMapper.INSTANCE.toCompilationDtos(compilations);
    }

    public CompilationDto getCompilation(Integer compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(NotFoundException::new);
        return CompilationMapper.INSTANCE.toCompilationDto(compilation);
    }

    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events = eventRepository.findAllByIdIn(newCompilationDto.getEvents());
        Compilation compilation = CompilationMapper.INSTANCE.toCompilation(newCompilationDto);
        compilation.setEvents(events);
        compilationRepository.save(compilation);
        return CompilationMapper.INSTANCE.toCompilationDto(compilation);
    }

    public void deleteCompilation(Integer compId) {
        if (!compilationRepository.existsById(compId)) throw new NotFoundException("Подборка не найдена");
        compilationRepository.deleteById(compId);
    }

    public CompilationDto updateCompilation(Integer compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException("Подборка не найдена"));
        Boolean pinned = updateCompilationRequest.getPinned();
        String title = updateCompilationRequest.getTitle();
        List<Integer> events = updateCompilationRequest.getEvents();
        if (pinned != null) compilation.setPinned(pinned);
        if (title != null) compilation.setTitle(title);
        if (events != null) {
            List<Event> eventsFromDb = eventRepository.findAllByIdIn(events);
            compilation.setEvents(eventsFromDb);
        }
        return CompilationMapper.INSTANCE.toCompilationDto(compilation);
    }
}
