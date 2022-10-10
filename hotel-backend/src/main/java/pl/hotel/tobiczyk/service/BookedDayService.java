package pl.hotel.tobiczyk.service;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import pl.hotel.tobiczyk.domain.dto.BlockRoomDto;
import pl.hotel.tobiczyk.domain.exception.InvalidDateRangeException;
import pl.hotel.tobiczyk.domain.model.BookedDay;
import pl.hotel.tobiczyk.repository.BookedDayRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BookedDayService {
    private BookedDayRepository bookedDaysRepository;
    private RoomService roomService;

    public BookedDayService(BookedDayRepository bookedDaysRepository, RoomService roomService) {
        this.bookedDaysRepository = bookedDaysRepository;
        this.roomService = roomService;
    }

    public List<BookedDay> findBookedDaysForRoom(final Long roomId) {
        return bookedDaysRepository.findAllByRoomId(roomId);
    }

    public void blockRoom(final BlockRoomDto blockRoomDto) {
        if(blockRoomDto.getDateTo().isBefore(blockRoomDto.getDateFrom()))
            throw new InvalidDateRangeException();

        blockRoomDto.getDateFrom().datesUntil(blockRoomDto.getDateTo()).forEach(day ->
            {
                try {
                    bookedDaysRepository.save(
                        BookedDay.builder()
                            .bookedDay(day)
                            .room(roomService.findRoomById(blockRoomDto.getRoomId())
                                .orElseThrow(() -> new NoSuchElementException()))
                            .build()
                    );
                    System.out.println("Blocked day: " + day.toString() + "for room: " + blockRoomDto.getRoomId());
                } catch(Exception ex) {
                    System.out.println("Day already blocked!: " + day.toString() + "for room: " + blockRoomDto.getRoomId());
                }
            }
        );
    }

    public List<Pair<LocalDate, LocalDate>> showBlockedRangesOfDaysForRoom(Long roomId) {
        var listOfRanges = new ArrayList<Pair<LocalDate, LocalDate>>();
        var bookedDays = bookedDaysRepository.findAllByRoomId(roomId).stream()
            .sorted((a,b) -> a.getBookedDay().isBefore(b.getBookedDay()) ? -1 : 1)
            .collect(Collectors.toList());

        int start = 0, end;
        for(int i = 1; i < bookedDays.size(); i++) {
            if(!bookedDays.get(i-1).getBookedDay().plusDays(1L).equals(bookedDays.get(i).getBookedDay())){
                end = i-1;
                var range = Pair.of(bookedDays.get(start).getBookedDay(), bookedDays.get(end).getBookedDay().plusDays(1L));
                listOfRanges.add(range);
                start = i;
            }
            if(i == bookedDays.size() -1) {
                end = i;
                var range = Pair.of(bookedDays.get(start).getBookedDay(), bookedDays.get(end).getBookedDay().plusDays(1L));
                listOfRanges.add(range);
            }
        }

        return listOfRanges;
    }
}
