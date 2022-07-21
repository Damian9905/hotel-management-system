package pl.hotel.tobiczyk.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "photos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String filePath;

    @ManyToOne (targetEntity = RoomType.class)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomType roomType;
}