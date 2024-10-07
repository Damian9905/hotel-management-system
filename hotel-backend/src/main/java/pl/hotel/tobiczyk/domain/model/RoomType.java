package pl.hotel.tobiczyk.domain.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.With;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "room_types")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public record RoomType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,
    String roomType,
    String description,
    Integer numberOfPeople,
    @NotNull
    @With Double price,
    @OneToMany (targetEntity = Room.class, cascade = CascadeType.ALL, mappedBy = "roomType", fetch = FetchType.LAZY)
    @JsonIgnore
    Set<Room> rooms
) {
}
