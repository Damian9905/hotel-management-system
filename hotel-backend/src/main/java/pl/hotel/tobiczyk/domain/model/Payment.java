package pl.hotel.tobiczyk.domain.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "payments")
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public record Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,
    String paymentMethod,
    String paymentStatus,
    @OneToOne(mappedBy = "payment")
    Reservation reservation
) {

}
