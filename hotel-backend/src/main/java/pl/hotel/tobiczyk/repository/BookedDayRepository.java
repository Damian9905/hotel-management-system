package pl.hotel.tobiczyk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.hotel.tobiczyk.domain.model.BookedDay;

import java.util.List;

@Repository
public interface BookedDayRepository extends JpaRepository<BookedDay, Long> {

    List<BookedDay> findAllByRoomId(Long roomId);
}
