package pl.hotel.tobiczyk.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "booked_days")
@Builder
public record BookedDay(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,
    @Column(name = "booked_day")
    LocalDate bookedDay,
    @ManyToOne(targetEntity = Room.class)
    @JoinColumn(name = "room_id", nullable = false)
    Room room
) {
}
