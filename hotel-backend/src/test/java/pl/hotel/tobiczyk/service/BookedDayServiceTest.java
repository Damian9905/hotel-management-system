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

    assertEquals(2, result.size());
    assertEquals(result.get(0).getFirst(), LocalDate.of(2022, 8, 01));
    assertEquals(result.get(0).getSecond(), LocalDate.of(2022, 8, 04));
    assertEquals(result.get(1).getFirst(), LocalDate.of(2022, 8, 05));
    assertEquals(result.get(1).getSecond(), LocalDate.of(2022, 8, 07));
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
            .bookedDay(LocalDate.of(2022, 8, 06))
            .build()
    );

    var bookedDaysRepository = mock(BookedDayRepository.class);
    when(bookedDaysRepository.findAllByRoomId(any())).thenReturn(bookedDays);

    var sut = new BookedDayService(bookedDaysRepository, mock(RoomService.class));

    var result = sut.getBlockedRangesOfDaysForRoom(1L);

    assertEquals(3, result.size());
    assertEquals(result.get(0).getFirst(), LocalDate.of(2022, 8, 01));
    assertEquals(result.get(0).getSecond(), LocalDate.of(2022, 8, 02));
    assertEquals(result.get(1).getFirst(), LocalDate.of(2022, 8, 03));
    assertEquals(result.get(1).getSecond(), LocalDate.of(2022, 8, 04));
    assertEquals(result.get(2).getFirst(), LocalDate.of(2022, 8, 05));
    assertEquals(result.get(2).getSecond(), LocalDate.of(2022, 8, 07));

  }

  @Test
  void showAllBookedDays_emptyList_shouldNotThrowException() {
    List<BookedDay> bookedDays = List.of();

    var bookedDaysRepository = mock(BookedDayRepository.class);
    when(bookedDaysRepository.findAllByRoomId(any())).thenReturn(bookedDays);

    var sut = new BookedDayService(bookedDaysRepository, mock(RoomService.class));

    assertDoesNotThrow(() -> sut.getBlockedRangesOfDaysForRoom(1L));
  }
}
