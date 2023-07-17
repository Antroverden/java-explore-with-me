package ru.practicum.mainservice.controller.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.model.response.ParticipationRequestDto;
import ru.practicum.mainservice.service.RequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestController {

    RequestService requestService;

    @GetMapping
    public List<ParticipationRequestDto> getRequests(@PathVariable Integer userId) {
        return requestService.getRequests(userId);
    }

    @PostMapping
    public ParticipationRequestDto addRequest(@PathVariable Integer userId, @RequestParam Integer eventId) {
        return requestService.addRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Integer userId, @PathVariable Integer requestId) {
        return requestService.cancelRequest(userId, requestId);
    }
}
