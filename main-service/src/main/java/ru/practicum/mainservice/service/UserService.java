package ru.practicum.mainservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.entity.User;
import ru.practicum.mainservice.exception.ConflictException;
import ru.practicum.mainservice.exception.NotFoundException;
import ru.practicum.mainservice.mapper.UserMapper;
import ru.practicum.mainservice.model.request.NewUserRequest;
import ru.practicum.mainservice.model.response.UserDto;
import ru.practicum.mainservice.storage.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;

    public List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size) {
        if (ids == null) {
            List<User> users = userRepository.findAll(PageRequest.of(from / size, size)).getContent();
            return UserMapper.INSTANCE.toUserDtos(users);
        }
        List<User> users = userRepository.findAllByIdIn(ids, PageRequest.of(from / size, size)).getContent();
        return UserMapper.INSTANCE.toUserDtos(users);
    }

    public UserDto createUser(NewUserRequest newUserRequest) {
        User user = UserMapper.INSTANCE.toUser(newUserRequest);
        try {
            User savedUser = userRepository.save(user);
            return UserMapper.INSTANCE.toUserDto(savedUser);
        } catch (DataAccessException e) {
            throw new ConflictException("Название категории " + user.getName() + " уже существует");
        }
    }

    public void deleteUser(Integer userId) {
        if (!userRepository.existsById(userId)) throw new NotFoundException("Юзер не найден");
        userRepository.deleteById(userId);
    }
}
