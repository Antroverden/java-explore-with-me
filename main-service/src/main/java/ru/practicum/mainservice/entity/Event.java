package ru.practicum.mainservice.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
    Long confirmedRequests;
    String createdOn;
    String description;
    String eventDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User initiator;
    @Embedded
    Location location;
    Boolean paid;
    Integer participantLimit;
    String publishedOn;
    Boolean requestModeration;
    State state;
    String title;

    public enum State {PENDING, PUBLISHED, CANCELED}
}
