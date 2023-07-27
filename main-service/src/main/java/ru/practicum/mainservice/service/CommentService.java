package ru.practicum.mainservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.entity.Comment;
import ru.practicum.mainservice.entity.Event;
import ru.practicum.mainservice.entity.User;
import ru.practicum.mainservice.exception.NotFoundException;
import ru.practicum.mainservice.mapper.CommentMapper;
import ru.practicum.mainservice.model.request.CommentDto;
import ru.practicum.mainservice.model.request.UpdateCommentRequest;
import ru.practicum.mainservice.storage.CommentRepository;
import ru.practicum.mainservice.storage.EventRepository;
import ru.practicum.mainservice.storage.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService {

    CommentRepository commentRepository;
    CommentMapper commentMapper;
    UserRepository userRepository;
    EventRepository eventRepository;

    public CommentDto addCommentToEvent(CommentDto commentDto, Integer userId, Integer eventId) {
        Comment comment = commentMapper.toComment(commentDto);
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        comment.setAuthor(user);
        comment.setEvent(event);
        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    public CommentDto changeComment(Integer eventId, Integer commentId, Integer userId,
                                    UpdateCommentRequest updateCommentRequest) {
        Comment comment = commentRepository.findByIdAndEvent_IdAndAuthor_Id(commentId, eventId, userId)
                .orElseThrow(NotFoundException::new);
        commentMapper.updateComment(comment, updateCommentRequest);
        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    public CommentDto changeComment(Integer eventId, Integer commentId, UpdateCommentRequest updateCommentRequest) {
        Comment comment = commentRepository.findByIdAndEvent_Id(commentId, eventId).orElseThrow(NotFoundException::new);
        commentMapper.updateComment(comment, updateCommentRequest);
        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    public void deleteComment(Integer eventId, Integer commentId) {
        commentRepository.deleteByIdAndEvent_Id(commentId, eventId).orElseThrow(NotFoundException::new);
    }

    public void deleteComment(Integer userId, Integer eventId, Integer commentId) {
        commentRepository.deleteByIdAndEvent_IdAndAuthor_Id(commentId, eventId, userId)
                .orElseThrow(NotFoundException::new);
    }

    public List<CommentDto> getComments(Integer eventId, Integer from, Integer size) {
        List<Comment> comments = commentRepository.findAllByEvent_Id(eventId, PageRequest.of(from / size, size))
                .getContent();
        return commentMapper.toCommentDtos(comments);
    }
}
