package ru.practicum.mainservice.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateResult {
    List<ParticipationRequestDto> confirmedRequests;
    List<ParticipationRequestDto> rejectedRequests;
}
