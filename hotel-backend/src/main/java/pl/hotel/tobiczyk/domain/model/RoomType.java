package pl.hotel.tobiczyk.domain.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "room_types")
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomType;
    private String description;
    private Integer numberOfPeople;
    @NotNull
    private Double price;

    @OneToMany (targetEntity = Room.class, cascade = CascadeType.ALL, mappedBy = "roomType", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Room> rooms;
}
