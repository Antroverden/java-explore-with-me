package ru.practicum.mainservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.entity.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
}
