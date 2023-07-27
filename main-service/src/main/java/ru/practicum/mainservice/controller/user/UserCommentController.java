package ru.practicum.mainservice.controller.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.model.response.CommentDto;
import ru.practicum.mainservice.model.request.NewCommentDto;
import ru.practicum.mainservice.service.CommentService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events/{eventId}/comment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserCommentController {

    CommentService commentService;

    @PostMapping
    public CommentDto addCommentToEvent(@RequestBody NewCommentDto newCommentDto,
                                        @PathVariable Integer userId,
                                        @PathVariable Integer eventId) {
        return commentService.addCommentToEvent(newCommentDto, userId, eventId);
    }

    @PatchMapping("{commentId}")
    public CommentDto changeComment(@Valid @RequestBody NewCommentDto newCommentDto,
                                    @PathVariable Integer userId,
                                    @PathVariable Integer eventId,
                                    @PathVariable Integer commentId) {
        return commentService.changeComment(userId, eventId, commentId, newCommentDto);
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
