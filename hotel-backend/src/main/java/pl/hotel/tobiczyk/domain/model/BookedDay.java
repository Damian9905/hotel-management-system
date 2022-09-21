package pl.hotel.tobiczyk.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "booked_days")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookedDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booked_day")
    private LocalDate bookedDay;

    @ManyToOne(targetEntity = Room.class)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
}
