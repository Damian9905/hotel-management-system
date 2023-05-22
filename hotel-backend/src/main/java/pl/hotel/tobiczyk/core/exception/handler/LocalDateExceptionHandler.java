package pl.hotel.tobiczyk.core.exception.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.hotel.tobiczyk.common.constants.TemplateConstants;
import pl.hotel.tobiczyk.domain.dto.BlockRoomDto;
import pl.hotel.tobiczyk.domain.dto.SearchDto;
import pl.hotel.tobiczyk.core.exception.DateException;
import pl.hotel.tobiczyk.core.exception.DayAlreadyBookedException;
import pl.hotel.tobiczyk.core.exception.InvalidDateRangeException;
import pl.hotel.tobiczyk.service.BookedDayService;
import pl.hotel.tobiczyk.service.RoomService;

import java.time.DateTimeException;

@ControllerAdvice
public class LocalDateExceptionHandler {
  private RoomService roomService;
  private BookedDayService bookedDayService;

  public LocalDateExceptionHandler(final RoomService roomService, final BookedDayService bookedDayService) {
    this.roomService = roomService;
    this.bookedDayService = bookedDayService;
  }

  @ExceptionHandler({InvalidDateRangeException.class, DayAlreadyBookedException.class, DateTimeException.class})
  public String handle(final DateException ex, final Model model) {
    if(ex.getTemplate().equals(TemplateConstants.BLOCK_ROOM_TEMPLATE)){
      model.addAttribute("exception", ex.getMessage());
      model.addAttribute("blockRoomDto", new BlockRoomDto());
      model.addAttribute("rooms", roomService.findAllRooms());
      model.addAttribute("ranges", bookedDayService.getBlockedDaysForAllRooms());

    } else if(ex.getTemplate().equals(TemplateConstants.SEARCH_ROOM_TEMPLATE)) {
      model.addAttribute("exception", ex.getMessage());
      model.addAttribute("searchDto", new SearchDto());
    }
    return ex.getTemplate();
  }
}
