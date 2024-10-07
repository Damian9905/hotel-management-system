package pl.hotel.tobiczyk.service;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;

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
                .description(toCreate.description())
                .name(toCreate.name())
                .roomType(roomTypeRepository.findById(toCreate.roomTypeId()).orElseThrow(NoSuchElementException::new))
                .build();
        return roomRepository.save(entity);
    }

    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }

    public void updateRoomTypePrice(final ChangePriceDto roomToChange) {
        RoomType room = roomTypeRepository.findById(roomToChange.id()).orElseThrow(NoSuchElementException::new);
        roomTypeRepository.save(room.withPrice(Optional.of(roomToChange.price().doubleValue()).orElse(room.price())));
    }
}
