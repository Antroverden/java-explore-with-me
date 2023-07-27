package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import ru.practicum.mainservice.entity.Comment;
import ru.practicum.mainservice.model.request.CommentDto;
import ru.practicum.mainservice.model.request.UpdateCommentRequest;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface CommentMapper {

    Comment toComment(CommentDto dto);

    CommentDto toCommentDto(Comment comment);

    List<CommentDto> toCommentDtos(List<Comment> comments);

    void updateComment(@MappingTarget Comment comment, UpdateCommentRequest dto);
}
