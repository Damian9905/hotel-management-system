package pl.hotel.tobiczyk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.hotel.tobiczyk.domain.model.RoomType;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    List<RoomType> findAll();

    Optional<RoomType> findById(Long Id);
}
