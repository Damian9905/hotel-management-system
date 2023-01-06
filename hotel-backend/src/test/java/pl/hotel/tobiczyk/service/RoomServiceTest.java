package pl.hotel.tobiczyk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.hotel.tobiczyk.domain.dto.ChangePriceDto;
import pl.hotel.tobiczyk.repository.RoomRepository;
import pl.hotel.tobiczyk.repository.RoomTypeRepository;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomServiceTest {
    private RoomService roomService;

    @Mock
    private RoomTypeRepository roomTypeRepository;
    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        roomService = new RoomService(roomRepository, roomTypeRepository);
    }

  //Optional<RoomType> getRoomType() {
  //   var roomType = new RoomType();
  //   roomType.setId(1L);
  //   return Optional.of(roomType);
  //}

 //@Test
 //void validEntity_shouldCreateAndReturnNewRoom() {
 //    when(roomTypeRepository.findById(any())).thenReturn(getRoomType());
 //    when(roomRepository.save(any())).thenReturn(inMemoryRoomRepository.save())

 //    var toCreate = new RoomWriteModel();
 //    toCreate.setDescription("description");
 //    toCreate.setName("name");
 //    toCreate.setRoomTypeId(1L);

 //    var result = roomService.createNewRoom(toCreate);

 //    assertEquals(1L, result.getId());
 //    assertEquals("name", result.getName());
 //    assertEquals("description", result.getDescription());
 //    assertNotNull(result.getRoomType());
 //    assertEquals(1, result.getRoomType().getId());
 //}

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
}