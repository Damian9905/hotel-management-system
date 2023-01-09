package pl.hotel.tobiczyk.service;

import org.springframework.stereotype.Service;
import pl.hotel.tobiczyk.domain.dto.ChangePriceDto;
import pl.hotel.tobiczyk.domain.dto.RoomWriteModel;
import pl.hotel.tobiczyk.domain.model.Room;
import pl.hotel.tobiczyk.domain.model.RoomType;
import pl.hotel.tobiczyk.repository.RoomRepository;
import pl.hotel.tobiczyk.repository.RoomTypeRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public Optional<Room> findRoomById(final Long id) {
        return roomRepository.findById(id);
    }

    public List<RoomType> findAllRoomTypes() {
        return roomTypeRepository.findAll();
    }

    public Optional<RoomType> findRoomTypeById(Long id) {
        return roomTypeRepository.findById(id);
    }

    public Room createNewRoom(RoomWriteModel toCreate) {
        Room entity = Room.builder()
                .description(toCreate.getDescription())
                .name(toCreate.getName())
                .roomType(roomTypeRepository.findById(toCreate.getRoomTypeId()).orElseThrow(NoSuchElementException::new))
                .build();
        return roomRepository.save(entity);
    }

    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }

    public void updateRoomTypePrice(final ChangePriceDto roomToChange) {
        var room = roomTypeRepository.findById(roomToChange.getId()).orElseThrow(NoSuchElementException::new);
        room.setPrice(Optional.ofNullable(roomToChange.getPrice().doubleValue()).orElse(room.getPrice()));
        roomTypeRepository.save(room);
    }
}
