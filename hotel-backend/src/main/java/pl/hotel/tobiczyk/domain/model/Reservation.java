package pl.hotel.tobiczyk.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Builder
public record Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,
    String customerId,
    LocalDate dateFrom,
    LocalDate dateTo,
    Double totalValue,
    @ManyToOne (targetEntity = Room.class)
    @JoinColumn(name = "room_id", nullable = false)
    Room room,
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    Payment payment
) {
}
