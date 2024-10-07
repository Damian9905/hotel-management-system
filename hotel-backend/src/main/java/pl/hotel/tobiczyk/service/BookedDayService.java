package pl.hotel.tobiczyk.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import pl.hotel.tobiczyk.common.constants.TemplateConstants;
import pl.hotel.tobiczyk.domain.dto.BlockRoomDto;
import pl.hotel.tobiczyk.domain.dto.RoomReadModel;
import pl.hotel.tobiczyk.core.exception.DayAlreadyBookedException;
import pl.hotel.tobiczyk.core.exception.InvalidDateRangeException;
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
@RequiredArgsConstructor
public class BookedDayService {
    private final BookedDayRepository bookedDaysRepository;
    private final RoomService roomService;

    public List<BookedDay> findBookedDaysForRoom(final Long roomId) {
        return bookedDaysRepository.findAllByRoomId(roomId);
    }

    public void blockRoom(final BlockRoomDto blockRoomDto) {
        if(blockRoomDto.dateTo().isBefore(blockRoomDto.dateFrom()))
            throw new InvalidDateRangeException(TemplateConstants.BLOCK_ROOM_TEMPLATE);

        blockRoomDto.dateFrom().datesUntil(blockRoomDto.dateTo()).forEach(day ->
            {
                try {
                    bookedDaysRepository.save(
                        BookedDay.builder()
                            .bookedDay(day)
                            .room(roomService.findRoomById(blockRoomDto.roomId())
                                .orElseThrow(NoSuchElementException::new))
                            .build()
                    );
                    log.info("Blocked day: " + day.toString() + "for room: " + blockRoomDto.roomId());
                } catch(Exception ex) {
                    log.warning("Day already blocked!: " + day.toString() + "for room: " + blockRoomDto.roomId());
                    throw new DayAlreadyBookedException(TemplateConstants.BLOCK_ROOM_TEMPLATE);
                }
            }
        );
    }

    List<Pair<LocalDate, LocalDate>> getBlockedRangesOfDaysForRoom(final Long roomId) {
        var listOfRanges = new ArrayList<Pair<LocalDate, LocalDate>>();
        var bookedDays = bookedDaysRepository.findAllByRoomId(roomId).stream()
            .sorted((a,b) -> a.bookedDay().isBefore(b.bookedDay()) ? -1 : 1)
            .toList();
        int start = 0;
        int end;
        for(int i = 1; i < bookedDays.size(); i++) {
            if(!bookedDays.get(i-1).bookedDay().plusDays(1L).equals(bookedDays.get(i).bookedDay())){
                end = i-1;
                var range = Pair.of(bookedDays.get(start).bookedDay(), bookedDays.get(end).bookedDay().plusDays(1L));
                listOfRanges.add(range);
                start = i;
            }
            if(i == bookedDays.size() -1) {
                end = i;
                var range = Pair.of(bookedDays.get(start).bookedDay(), bookedDays.get(end).bookedDay().plusDays(1L));
                listOfRanges.add(range);
            }
        }

        return listOfRanges;
    }

    public List<List<Pair<LocalDate, LocalDate>>> getBlockedDaysForAllRooms() {
        return roomService.findAllRooms().stream()
            .map(room -> getBlockedRangesOfDaysForRoom(room.id())
        ).collect(Collectors.toList());
    }

    public List<RoomReadModel> getAvailableRooms(final LocalDate dateFrom, final LocalDate dateTo) {
        if(dateTo.isBefore(dateFrom))
            throw new InvalidDateRangeException(TemplateConstants.SEARCH_ROOM_TEMPLATE);

        final var days = retrieveDatesToCheck(dateFrom, dateTo);
        return roomService.findAllRooms().stream()
            .filter(room ->
                room.bookedDays()
                    .stream().map(BookedDay::bookedDay)
                    .noneMatch(days::contains)
            ).map(RoomReadModel::fromRoom)
            .collect(Collectors.toList());
    }

    private List<LocalDate> retrieveDatesToCheck(final LocalDate dateFrom, final LocalDate dateTo) {
        return dateFrom.datesUntil(dateTo, Period.ofDays(1)).toList();
    }

    public Integer countNights(final LocalDate dateFrom, final LocalDate dateTo) {
        if(dateTo.isBefore(dateFrom))
            throw new InvalidDateRangeException(TemplateConstants.SEARCH_ROOM_TEMPLATE);
        return retrieveDatesToCheck(dateFrom, dateTo).size();
    }
}
