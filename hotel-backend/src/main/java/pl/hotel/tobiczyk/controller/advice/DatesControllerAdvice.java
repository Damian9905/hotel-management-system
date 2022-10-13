package pl.hotel.tobiczyk.controller.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.hotel.tobiczyk.domain.constants.TemplateConstants;
import pl.hotel.tobiczyk.domain.dto.BlockRoomDto;
import pl.hotel.tobiczyk.domain.dto.SearchDto;
import pl.hotel.tobiczyk.domain.exception.DateException;
import pl.hotel.tobiczyk.domain.exception.DayAlreadyBookedException;
import pl.hotel.tobiczyk.domain.exception.InvalidDateRangeException;
import pl.hotel.tobiczyk.service.BookedDayService;
import pl.hotel.tobiczyk.service.RoomService;

@ControllerAdvice
public class DatesControllerAdvice {
  private RoomService roomService;
  private BookedDayService bookedDayService;

  public DatesControllerAdvice(final RoomService roomService, final BookedDayService bookedDayService) {
    this.roomService = roomService;
    this.bookedDayService = bookedDayService;
  }

  @ExceptionHandler({InvalidDateRangeException.class, DayAlreadyBookedException.class})
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
