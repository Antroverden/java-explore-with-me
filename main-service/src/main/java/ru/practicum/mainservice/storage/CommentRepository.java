package ru.practicum.mainservice.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.entity.Comment;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Page<Comment> findAllByEvent_Id(Integer eventId, Pageable pageable);

    void deleteByIdAndEvent_Id(Integer commentId, Integer eventId);

    Boolean existsByIdAndEvent_IdAndAuthor_Id(Integer commentId, Integer eventId, Integer authorId);

    Boolean existsByIdAndEvent_Id(Integer commentId, Integer eventId);

    void deleteByIdAndEvent_IdAndAuthor_Id(Integer commentId, Integer eventId, Integer authorId);

    Optional<Comment> findByIdAndEvent_IdAndAuthor_Id(Integer commentId, Integer eventId, Integer authorId);

    Optional<Comment> findByIdAndEvent_Id(Integer commentId, Integer eventId);
}
