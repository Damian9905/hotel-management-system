package pl.hotel.tobiczyk.domain.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rooms")
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public record Room(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,
    String name,
    String description,
    @ManyToOne (targetEntity = RoomType.class)
    @JoinColumn(name = "room_type_id", nullable = false)
    RoomType roomType,
    @OneToMany (targetEntity = BookedDay.class, cascade = CascadeType.ALL, mappedBy = "room", fetch = FetchType.LAZY)
    @JsonIgnore
    Set<BookedDay> bookedDays,
    @OneToMany (targetEntity = Reservation.class, cascade = CascadeType.ALL, mappedBy = "room", fetch = FetchType.LAZY)
    @JsonIgnore
    Set<Reservation> reservations
) {
}
