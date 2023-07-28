package ru.practicum.mainservice.controller.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.model.response.CommentDto;
import ru.practicum.mainservice.service.CommentService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events/{eventId}/comment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserCommentController {

    CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ru.practicum.mainservice.model.response.CommentDto addCommentToEvent(@Valid @RequestBody CommentDto commentDto,
                                                                                @PathVariable Integer userId,
                                                                                @PathVariable Integer eventId) {
        return commentService.addCommentToEvent(commentDto, userId, eventId);
    }

    @PatchMapping("{commentId}")
    public ru.practicum.mainservice.model.response.CommentDto changeComment(@Valid @RequestBody CommentDto commentDto,
                                                                            @PathVariable Integer userId,
                                                                            @PathVariable Integer eventId,
                                                                            @PathVariable Integer commentId) {
        return commentService.changeComment(userId, eventId, commentId, commentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public String deleteComment(@PathVariable Integer userId,
                                @PathVariable Integer eventId,
                                @PathVariable Integer commentId) {
        commentService.deleteComment(userId, eventId, commentId);
        return "Комментарий удален";
    }
}
