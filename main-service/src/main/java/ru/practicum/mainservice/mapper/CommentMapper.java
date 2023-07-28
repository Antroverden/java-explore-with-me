package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.practicum.mainservice.entity.Comment;
import ru.practicum.mainservice.model.response.CommentDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment toComment(CommentDto dto);

    ru.practicum.mainservice.model.response.CommentDto toCommentDto(Comment comment);

    List<ru.practicum.mainservice.model.response.CommentDto> toCommentDtos(List<Comment> comments);

    void updateComment(@MappingTarget Comment comment, CommentDto dto);
}
