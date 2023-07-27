package ru.practicum.mainservice.controller.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.model.request.CommentDto;
import ru.practicum.mainservice.model.request.UpdateCommentRequest;
import ru.practicum.mainservice.service.CommentService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/events/{eventId}/comment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminCommentController {

    CommentService commentService;

    @PatchMapping("{commentId}")
    public CommentDto changeComment(
            @Valid @RequestBody UpdateCommentRequest updateCommentRequest,
            @PathVariable Integer eventId,
            @PathVariable Integer commentId) {
        return commentService.changeComment(eventId, commentId, updateCommentRequest);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public String deleteComment(@PathVariable Integer eventId,
                                @PathVariable Integer commentId) {
        commentService.deleteComment(eventId, commentId);
        return "Комментарий удален";
    }
}
