package ru.practicum.mainservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
