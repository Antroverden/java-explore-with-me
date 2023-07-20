package ru.practicum.mainservice.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.entity.Compilation;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
    Page<Compilation> findCompilationsByPinnedTrue(Pageable pageable);
}
