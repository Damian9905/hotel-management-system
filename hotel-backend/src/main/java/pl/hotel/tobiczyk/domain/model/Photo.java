package pl.hotel.tobiczyk.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "photos")
@Builder
public record Photo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,
    String fileName,
    String filePath,
    @ManyToOne (targetEntity = RoomType.class)
    @JoinColumn(name = "room_type_id", nullable = false)
    RoomType roomType
) {

}