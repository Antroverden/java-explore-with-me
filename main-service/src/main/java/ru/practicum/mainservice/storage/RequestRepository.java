package ru.practicum.mainservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.entity.ParticipationRequest;

import java.util.List;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Integer> {
    List<ParticipationRequest> findAllByRequester_Id(Integer userId);
    List<ParticipationRequest> findAllByRequester_IdAndEvent_Id(Integer userId, Integer eventId);
    Boolean existsParticipationRequestByRequester_idAndEvent_Id(Integer userId, Integer eventId);
    Boolean existsAllByIdInAndStatus(List<Integer> ids, ParticipationRequest.Status status);
}
