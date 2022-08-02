package pl.hotel.tobiczyk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.hotel.tobiczyk.domain.model.Room;

import java.util.List;

@Repository
public interface RoomRepository extends SpecificRoomRepository, JpaRepository<Room, Long> {

    @Query(value = "select distinct r from Room r order by r.id asc")
    List<Room> findAll();

    @Override
    Room save(Room entity);
}
