package pl.hotel.tobiczyk.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToOne (targetEntity = RoomType.class)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomType roomType;
}
