package pl.hotel.tobiczyk.controller;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.hotel.tobiczyk.domain.dto.BlockRoomDto;
import pl.hotel.tobiczyk.domain.model.Room;
import pl.hotel.tobiczyk.service.BookedDayService;
import pl.hotel.tobiczyk.service.RoomService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
class BookingController {
    private BookedDayService bookedDaysService;
    private RoomService roomService;

    public BookingController(BookedDayService bookedDaysService, RoomService roomService) {
        this.bookedDaysService = bookedDaysService;
        this.roomService = roomService;
    }

    @GetMapping("panel/admin/blockRoom")
    public String blockRoomForm(Model model) {
        model.addAttribute("blockRoomDto", new BlockRoomDto());
        model.addAttribute("rooms", roomService.findAllRooms());
        var rooms =  roomService.findAllRooms();
        List<List<Pair<LocalDate, LocalDate>>> ranges = new ArrayList<>();
        for(Room room: rooms) {
            ranges.add(Optional.ofNullable(bookedDaysService.showBlockedRangesOfDaysForRoom(room.getId()))
                .orElse(List.of()));
        }
        model.addAttribute("ranges", ranges);
        return "blockRoom";
    }

    @PostMapping("/panel/admin/blockRoom")
    public String blockRoom(@ModelAttribute @Valid BlockRoomDto blockRoomDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return blockRoomForm(model);
        }
        bookedDaysService.blockRoom(blockRoomDto);
        return blockRoomForm(model);
    }
}
