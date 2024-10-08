package pl.hotel.tobiczyk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.hotel.tobiczyk.domain.dto.BlockRoomDto;
import pl.hotel.tobiczyk.domain.dto.ChangePriceDto;
import pl.hotel.tobiczyk.domain.dto.RoomWriteModel;
import pl.hotel.tobiczyk.domain.model.Room;
import pl.hotel.tobiczyk.service.BookedDayService;
import pl.hotel.tobiczyk.service.PhotoService;
import pl.hotel.tobiczyk.service.RoomService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
class RoomController {
    private static final String ROOM_TYPES = "roomTypes";
    private static final String ROOMS = "rooms";
    private static final String ROOMS_TEMPLATE = "staticContent/rooms";
    private static final String ERRORS = "errors";

    private final RoomService roomService;
    private final PhotoService photoService;
    private final BookedDayService bookedDayService;

    @ModelAttribute("rooms")
    List<Room> readAllRooms() {
        return roomService.findAllRooms();
    }

    @GetMapping(path = "/rooms")
    public String showAllRooms(final Model model) {
        model.addAttribute(ROOM_TYPES, roomService.findAllRoomTypes());
        model.addAttribute("photos", photoService.showAllPhotos());
        return ROOMS_TEMPLATE;
    }


    @GetMapping("/panel/admin/addRoom")
    public String addRoomForm(final Model model) {
        model.addAttribute("roomWriteModel", new RoomWriteModel());
        model.addAttribute(ROOM_TYPES, roomService.findAllRoomTypes());
        return "adminPanel/addRoom";
    }

    @PostMapping("/panel/admin/addRoom")
    public String addRoom(@ModelAttribute @Valid final RoomWriteModel roomWriteModel,
                          final BindingResult result, final Model model) {
        if(result.hasErrors()) {
            model.addAttribute(ERRORS, result.getAllErrors());
            return addRoomForm(model);
        }
        roomService.createNewRoom(roomWriteModel);
        return "adminPanel/addRoom";
    }


    @GetMapping("/panel/admin/editRooms")
    public String editRooms(final Model model) {
        model.addAttribute(ROOMS, roomService.findAllRooms());
        return "adminPanel/editRooms";
    }

    @PostMapping("panel/admin/deleteRoom")
    public String deleteRoom(@RequestParam("roomId") final Long id) {
        roomService.deleteRoom(id);
        return "redirect:/panel/admin/editRooms";
    }


    @GetMapping("/panel/admin/changeRoomPrice")
    public String changeRoomPriceForm(final Model model) {
        model.addAttribute("changePriceDto", new ChangePriceDto());
        model.addAttribute(ROOM_TYPES, roomService.findAllRoomTypes());
        return "adminPanel/changeRoomPrice";
    }

    @PostMapping("/panel/admin/changeRoomPrice")
    public String changeRoomPrice(@ModelAttribute @Valid final ChangePriceDto changePriceDto,
                                  final BindingResult result, final Model model) {
        if(result.hasErrors()) {
            model.addAttribute(ERRORS, result.getAllErrors());
            return changeRoomPriceForm(model);
        }
        roomService.updateRoomTypePrice(changePriceDto);
        return "adminPanel/changeRoomPrice";
    }

    @GetMapping("panel/admin/blockRoom")
    public String blockRoomForm(final Model model) {
        model.addAttribute("blockRoomDto", new BlockRoomDto());
        model.addAttribute(ROOMS, roomService.findAllRooms());
        model.addAttribute("ranges", bookedDayService.getBlockedDaysForAllRooms());
        return "adminPanel/blockRoom";
    }

    @PostMapping("/panel/admin/blockRoom")
    public String blockRoom(@ModelAttribute @Valid final BlockRoomDto blockRoomDto,
                            final BindingResult result, final Model model) {
        if(result.hasErrors()) {
            model.addAttribute(ERRORS, result.getAllErrors());
            return blockRoomForm(model);
        }
        bookedDayService.blockRoom(blockRoomDto);
        return blockRoomForm(model);
    }
}
