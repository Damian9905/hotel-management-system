package pl.hotel.tobiczyk.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String customerId;
  private LocalDate dateFrom;
  private LocalDate dateTo;
  private Double totalValue;
  @ManyToOne (targetEntity = Room.class)
  @JoinColumn(name = "room_id", nullable = false)
  private Room room;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "payment_id", referencedColumnName = "id")
  private Payment payment;

}
