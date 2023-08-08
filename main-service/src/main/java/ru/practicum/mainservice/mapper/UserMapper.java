package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import ru.practicum.mainservice.entity.User;
import ru.practicum.mainservice.model.request.NewUserRequest;
import ru.practicum.mainservice.model.response.UserDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(NewUserRequest dto);

    UserDto toUserDto(User user);

    List<UserDto> toUserDtos(List<User> users);
}
