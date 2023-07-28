package ru.practicum.mainservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.entity.Comment;
import ru.practicum.mainservice.entity.Event;
import ru.practicum.mainservice.entity.User;
import ru.practicum.mainservice.exception.NotFoundException;
import ru.practicum.mainservice.mapper.CommentMapper;
import ru.practicum.mainservice.model.response.CommentDto;
import ru.practicum.mainservice.storage.CommentRepository;
import ru.practicum.mainservice.storage.EventRepository;
import ru.practicum.mainservice.storage.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService {

    CommentRepository commentRepository;
    CommentMapper commentMapper;
    UserRepository userRepository;
    EventRepository eventRepository;

    public ru.practicum.mainservice.model.response.CommentDto addCommentToEvent(CommentDto commentDto, Integer userId, Integer eventId) {
        Comment comment = commentMapper.toComment(commentDto);
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        comment.setAuthor(user);
        comment.setEvent(event);
        comment.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    public ru.practicum.mainservice.model.response.CommentDto changeComment(Integer userId, Integer eventId, Integer commentId,
                                                                            CommentDto commentDto) {
        Comment comment = commentRepository.findByIdAndEvent_IdAndAuthor_Id(commentId, eventId, userId)
                .orElseThrow(NotFoundException::new);
        commentMapper.updateComment(comment, commentDto);
        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    public ru.practicum.mainservice.model.response.CommentDto changeComment(Integer eventId, Integer commentId, CommentDto commentDto) {
        Comment comment = commentRepository.findByIdAndEvent_Id(commentId, eventId).orElseThrow(NotFoundException::new);
        commentMapper.updateComment(comment, commentDto);
        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    @Transactional
    public void deleteComment(Integer eventId, Integer commentId) {
        if (!commentRepository.existsByIdAndEvent_Id(commentId, eventId)) {
            throw new NotFoundException("Данный комментарий у события с айди " + eventId + " отсутствует");
        }
        commentRepository.deleteByIdAndEvent_Id(commentId, eventId);
    }

    @Transactional
    public void deleteComment(Integer userId, Integer eventId, Integer commentId) {
        if (!commentRepository.existsByIdAndEvent_IdAndAuthor_Id(commentId, eventId, userId)) {
            throw new NotFoundException("У юзера с айди " + userId + " отсутствует данный комментарий");
        }
        commentRepository.deleteByIdAndEvent_IdAndAuthor_Id(commentId, eventId, userId);
    }

    public List<ru.practicum.mainservice.model.response.CommentDto> getComments(Integer eventId, Integer from, Integer size) {
        List<Comment> comments = commentRepository.findAllByEvent_Id(eventId, PageRequest.of(from / size, size))
                .getContent();
        return commentMapper.toCommentDtos(comments);
    }
}
