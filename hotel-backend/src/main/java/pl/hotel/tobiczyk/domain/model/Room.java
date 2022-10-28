package pl.hotel.tobiczyk.domain.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToOne (targetEntity = RoomType.class)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomType roomType;

    @OneToMany (targetEntity = BookedDay.class, cascade = CascadeType.ALL, mappedBy = "room", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<BookedDay> bookedDays;
    @OneToMany (targetEntity = Reservation.class, cascade = CascadeType.ALL, mappedBy = "room", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Reservation> reservations;
}
