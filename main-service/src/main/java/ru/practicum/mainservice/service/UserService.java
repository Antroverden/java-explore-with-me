package ru.practicum.mainservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.model.request.NewUserRequest;
import ru.practicum.mainservice.model.response.UserDto;
import ru.practicum.mainservice.storage.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;

    public List<UserDto> getUsers(Integer[] ids, Integer from, Integer size) {

    }

    public UserDto createUser(NewUserRequest newUserRequest) {

    }

    public void deleteUser(Integer userId) {

    }
}
