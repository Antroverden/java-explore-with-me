package ru.practicum.mainservice.controller.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.model.request.UpdateEventAdminRequest;
import ru.practicum.mainservice.model.response.EventFullDto;
import ru.practicum.mainservice.service.EventService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventController {

    EventService eventService;

    @GetMapping
    public List<EventFullDto> getEvents(
            @RequestParam Integer[] users,
            @RequestParam String[] states,
            @RequestParam Integer[] categories,
            @RequestParam String rangeStart,
            @RequestParam String rangeEnd,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return eventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto changeEvent(
            @PathVariable Integer eventId,
            @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        return eventService.changeEvent(eventId, updateEventAdminRequest);
    }
}
