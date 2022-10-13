package pl.hotel.tobiczyk.service;

import org.junit.jupiter.api.Test;
import pl.hotel.tobiczyk.domain.model.BookedDay;
import pl.hotel.tobiczyk.repository.BookedDayRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookedDayServiceTest {

  @Test
  void showAllBookedDays_shouldCreateTwoPairOfRanges() {
    var bookedDays = List.of(
        BookedDay.builder()
            .bookedDay(LocalDate.of(2022, 8, 01))
            .build(),
        BookedDay.builder()
            .bookedDay(LocalDate.of(2022, 8, 02))
            .build(),
        BookedDay.builder()
            .bookedDay(LocalDate.of(2022, 8, 03))
            .build(),
        BookedDay.builder()
            .bookedDay(LocalDate.of(2022, 8, 05))
            .build(),
        BookedDay.builder()
            .bookedDay(LocalDate.of(2022, 8, 06))
            .build()
    );

    var bookedDaysRepository = mock(BookedDayRepository.class);
    when(bookedDaysRepository.findAllByRoomId(any())).thenReturn(bookedDays);

    var sut = new BookedDayService(bookedDaysRepository, mock(RoomService.class));

    var result = sut.getBlockedRangesOfDaysForRoom(1L);

    assertEquals(result.size(), 2);
    assertEquals(result.get(0).getFirst(), LocalDate.of(2022, 8, 01));
    assertEquals(result.get(0).getSecond(), LocalDate.of(2022, 8, 03));
    assertEquals(result.get(1).getFirst(), LocalDate.of(2022, 8, 05));
    assertEquals(result.get(1).getSecond(), LocalDate.of(2022, 8, 06));

  }

  @Test
  void showAllBookedDays_shouldCreateThreePairsOfRanges() {
    var bookedDays = List.of(
        BookedDay.builder()
            .bookedDay(LocalDate.of(2022, 8, 01))
            .build(),
        BookedDay.builder()
            .bookedDay(LocalDate.of(2022, 8, 03))
            .build(),
        BookedDay.builder()
            .bookedDay(LocalDate.of(2022, 8, 05))
            .build(),
        BookedDay.builder()
            .bookedDay(LocalDate.of(2022, 8, 07))
            .build()
    );

    var bookedDaysRepository = mock(BookedDayRepository.class);
    when(bookedDaysRepository.findAllByRoomId(any())).thenReturn(bookedDays);

    var sut = new BookedDayService(bookedDaysRepository, mock(RoomService.class));

    var result = sut.getBlockedRangesOfDaysForRoom(1L);

  }

  @Test
  void showAllBookedDays_emptyList_shouldNotThrowException() {
    List<BookedDay> bookedDays = List.of();

    var bookedDaysRepository = mock(BookedDayRepository.class);
    when(bookedDaysRepository.findAllByRoomId(any())).thenReturn(bookedDays);

    var sut = new BookedDayService(bookedDaysRepository, mock(RoomService.class));

    assertDoesNotThrow(() -> sut.findBookedDaysForRoom(1L));
  }

  //TODO:
  //test with invalid date format

}