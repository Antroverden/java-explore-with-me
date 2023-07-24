package ru.practicum.mainservice.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static ru.practicum.mainservice.entity.ParticipationRequest.Status;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
    Integer id;
    LocalDateTime created;
    Integer event;
    Integer requester;
    Status status;
}
