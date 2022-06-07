package pl.hotel.tobiczyk.service;

import org.springframework.stereotype.Service;
import pl.hotel.tobiczyk.domain.model.Room;
import pl.hotel.tobiczyk.repository.RoomRepository;

import java.util.List;

@Service
public class RoomService {
    private RoomRepository roomRepository;

    public RoomService(final RoomRepository repo){
        this.roomRepository = repo;
    }

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }
}
