package pl.hotel.tobiczyk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.hotel.tobiczyk.domain.dto.ChangePriceDto;
import pl.hotel.tobiczyk.domain.dto.RoomDto;
import pl.hotel.tobiczyk.domain.model.Room;
import pl.hotel.tobiczyk.domain.model.RoomType;
import pl.hotel.tobiczyk.repository.RoomRepository;
import pl.hotel.tobiczyk.repository.RoomTypeRepository;
import pl.hotel.tobiczyk.repository.SpecificRoomRepository;

import java.util.*;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomServiceTest {
    private RoomRepository inMemoryRoomRepository;
    private RoomService roomService;

    @Mock
    RoomTypeRepository roomTypeRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        //inMemoryRoomRepository = new InMemoryRoomRepository();
        roomService = new RoomService(inMemoryRoomRepository, roomTypeRepository);
    }

   Optional<RoomType> getRoomType() {
      var roomType = new RoomType();
      roomType.setId(1L);
      return Optional.of(roomType);
   }

   @Test
   void validEntity_shouldCreateAndReturnNewRoom() {
       when(roomTypeRepository.findById(any())).thenReturn(getRoomType());

       var toCreate = new RoomDto();
       toCreate.setDescription("description");
       toCreate.setName("name");
       toCreate.setRoomTypeId(1L);

       var result = roomService.createNewRoom(toCreate);

       assertEquals(1L, result.getId());
       assertEquals("name", result.getName());
       assertEquals("description", result.getDescription());
       assertNotNull(result.getRoomType());
       assertEquals(1, result.getRoomType().getId());
   }

    @Test
    void changePriceWithInvalidRoomTypeId_shouldThrowNoSuchElementException() {
       var roomRepository = mock(RoomRepository.class);
       var roomTypeRepository = mock(RoomTypeRepository.class);
       when(roomTypeRepository.findById(any())).thenReturn(Optional.empty());

       var sut = new RoomService(roomRepository, roomTypeRepository);
       var changePriceDto = new ChangePriceDto();
       changePriceDto.setId(10L);
       changePriceDto.setPrice(200);

       assertThrows(NoSuchElementException.class, () -> sut.updateRoomTypePrice(changePriceDto));
   }

   private static class InMemoryRoomRepository implements SpecificRoomRepository {
       private Long index = 0L;
       private Map<Long, Room> map = new HashMap<>();

       @Override
       public List<Room> findAll() {
           return map.values().stream().collect(Collectors.toList());
       }

       @Override
       public Room save(Room entity) {
           entity.setId(++index);
           map.put(entity.getId(), entity);
           return entity;
       }
   }
}