package ru.practicum.mainservice.controller.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.model.request.EventRequestStatusUpdateRequest;
import ru.practicum.mainservice.model.request.NewEventDto;
import ru.practicum.mainservice.model.request.UpdateEventUserRequest;
import ru.practicum.mainservice.model.response.*;
import ru.practicum.mainservice.service.EventService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventController {

    EventService eventService;

    @GetMapping
    public List<EventShortDto> getEvents(
            @PathVariable Integer userId,
            @RequestParam Integer from,
            @RequestParam Integer size
    ) {
        return eventService.getEvents(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEvent(@PathVariable Integer userId, @RequestBody NewEventDto newEventDto) {
        return eventService.addEvent(userId, newEventDto);
    }

    @GetMapping("{eventId}")
    public EventFullDto getEvent(@PathVariable Integer userId, @PathVariable Integer eventId) {
        return eventService.getEvent(userId, eventId);
    }

    @PatchMapping("{eventId}")
    public EventFullDto changeEvent(
            @PathVariable Integer userId,
            @PathVariable Integer eventId,
            @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return eventService.changeEvent(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("{eventId}/requests")
    public ParticipationRequestDto getEventRequests(@PathVariable Integer userId, @PathVariable Integer eventId) {
        return eventService.getEventRequests(userId, eventId);
    }

    @PatchMapping("{eventId}/requests")
    public EventRequestStatusUpdateResult changeEventRequests(
            @PathVariable Integer userId,
            @PathVariable Integer eventId,
            @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return eventService.changeEventRequests(userId, eventId, eventRequestStatusUpdateRequest);
    }
}
