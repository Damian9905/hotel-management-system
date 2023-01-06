package pl.hotel.tobiczyk.service;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import pl.hotel.tobiczyk.domain.constants.TemplateConstants;
import pl.hotel.tobiczyk.domain.dto.BlockRoomDto;
import pl.hotel.tobiczyk.domain.dto.RoomReadModel;
import pl.hotel.tobiczyk.domain.exception.DayAlreadyBookedException;
import pl.hotel.tobiczyk.domain.exception.InvalidDateRangeException;
import pl.hotel.tobiczyk.domain.model.BookedDay;
import pl.hotel.tobiczyk.repository.BookedDayRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Log
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
            throw new InvalidDateRangeException(TemplateConstants.BLOCK_ROOM_TEMPLATE);

        blockRoomDto.getDateFrom().datesUntil(blockRoomDto.getDateTo()).forEach(day ->
            {
                try {
                    bookedDaysRepository.save(
                        BookedDay.builder()
                            .bookedDay(day)
                            .room(roomService.findRoomById(blockRoomDto.getRoomId())
                                .orElseThrow(NoSuchElementException::new))
                            .build()
                    );
                    log.info("Blocked day: " + day.toString() + "for room: " + blockRoomDto.getRoomId());
                } catch(Exception ex) {
                    log.warning("Day already blocked!: " + day.toString() + "for room: " + blockRoomDto.getRoomId());
                    throw new DayAlreadyBookedException(TemplateConstants.BLOCK_ROOM_TEMPLATE);
                }
            }
        );
    }

    List<Pair<LocalDate, LocalDate>> getBlockedRangesOfDaysForRoom(final Long roomId) {
        var listOfRanges = new ArrayList<Pair<LocalDate, LocalDate>>();
        var bookedDays = bookedDaysRepository.findAllByRoomId(roomId).stream()
            .sorted((a,b) -> a.getBookedDay().isBefore(b.getBookedDay()) ? -1 : 1)
            .collect(Collectors.toList());
        int start = 0;
        int end;
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

    public List<List<Pair<LocalDate, LocalDate>>> getBlockedDaysForAllRooms() {
        return roomService.findAllRooms().stream()
            .map(room -> getBlockedRangesOfDaysForRoom(room.getId())
        ).collect(Collectors.toList());
    }

    public List<RoomReadModel> getAvailableRooms(final LocalDate dateFrom, final LocalDate dateTo) {
        if(dateTo.isBefore(dateFrom))
            throw new InvalidDateRangeException(TemplateConstants.SEARCH_ROOM_TEMPLATE);

        final var days = retrieveDatesToCheck(dateFrom, dateTo);
        return roomService.findAllRooms().stream()
            .filter(room ->
                room.getBookedDays()
                    .stream().map(BookedDay::getBookedDay)
                    .noneMatch(days::contains)
            ).map(RoomReadModel::fromRoom)
            .collect(Collectors.toList());
    }

    private List<LocalDate> retrieveDatesToCheck(final LocalDate dateFrom, final LocalDate dateTo) {
        return dateFrom.datesUntil(dateTo, Period.ofDays(1)).collect(Collectors.toList());
    }

    public Integer countNights(final LocalDate dateFrom, final LocalDate dateTo) {
        if(dateTo.isBefore(dateFrom))
            throw new InvalidDateRangeException(TemplateConstants.SEARCH_ROOM_TEMPLATE);
        return retrieveDatesToCheck(dateFrom, dateTo).size();
    }
}
