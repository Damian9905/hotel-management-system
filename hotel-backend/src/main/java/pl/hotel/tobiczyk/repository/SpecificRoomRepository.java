package pl.hotel.tobiczyk.repository;

import pl.hotel.tobiczyk.domain.model.Room;

import java.util.List;
import java.util.Optional;

public interface SpecificRoomRepository {
    List<Room> findAll();
    Room save(Room entity);

}
