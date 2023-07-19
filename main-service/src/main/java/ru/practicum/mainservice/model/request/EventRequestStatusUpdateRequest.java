package ru.practicum.mainservice.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateRequest {
    List<Integer> requestIds;
    Status status;

    public enum Status {CONFIRMED, REJECTED}
}
