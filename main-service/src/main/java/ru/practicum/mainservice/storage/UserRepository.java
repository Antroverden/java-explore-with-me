package ru.practicum.mainservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
