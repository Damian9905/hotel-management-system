package pl.hotel.tobiczyk.service;

import org.springframework.stereotype.Service;
import pl.hotel.tobiczyk.domain.dto.RoomDto;
import pl.hotel.tobiczyk.domain.model.Room;
import pl.hotel.tobiczyk.repository.RoomRepository;
import pl.hotel.tobiczyk.repository.RoomTypeRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RoomService {
    private RoomRepository roomRepository;
    private RoomTypeRepository roomTypeRepository;

    public RoomService(final RoomRepository repo, RoomTypeRepository roomTypeRepository){
        this.roomRepository = repo;
        this.roomTypeRepository = roomTypeRepository;
    }

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public Room createNewRoom(RoomDto toCreate) {
        Room entity = Room.builder()
                .description(toCreate.getDescription())
                .name(toCreate.getName())
                .roomType(roomTypeRepository.findById(toCreate.getRoomTypeId()).orElseThrow(NoSuchElementException::new))
                .build();
        return roomRepository.save(entity);
    }
}
