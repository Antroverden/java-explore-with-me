package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.mainservice.entity.Comment;
import ru.practicum.mainservice.model.response.CommentDto;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {

    Comment toComment(CommentDto dto);

    CommentDto toCommentDto(Comment comment);

    List<CommentDto> toCommentDtos(List<Comment> comments);

    void updateComment(@MappingTarget Comment comment, CommentDto dto);
}
