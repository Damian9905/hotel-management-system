package pl.hotel.tobiczyk.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.hotel.tobiczyk.domain.dto.BlockRoomDto;
import pl.hotel.tobiczyk.domain.exception.InvalidDateRangeException;
import pl.hotel.tobiczyk.service.RoomService;

@ControllerAdvice
public class DatesControllerAdvice {
  private static final String BLOCK_ROOM_TEMPLATE = "blockRoom";
  private RoomService roomService;

  public DatesControllerAdvice(RoomService roomService) {
    this.roomService = roomService;
  }

  @ExceptionHandler(InvalidDateRangeException.class)
  public String handle(InvalidDateRangeException ex, Model model) {
    model.addAttribute("exception", ex.getMessage());
    model.addAttribute("blockRoomDto", new BlockRoomDto());
    model.addAttribute("rooms", roomService.findAllRooms());
    return BLOCK_ROOM_TEMPLATE;
  }
}
