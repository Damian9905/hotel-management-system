package pl.hotel.tobiczyk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.hotel.tobiczyk.domain.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
