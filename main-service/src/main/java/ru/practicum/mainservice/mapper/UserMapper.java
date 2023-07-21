package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.mainservice.entity.User;
import ru.practicum.mainservice.model.request.NewUserRequest;
import ru.practicum.mainservice.model.response.UserDto;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(NewUserRequest dto);

    UserDto toUserDto(User user);

    List<UserDto> toUserDtos(List<User> users);
}
