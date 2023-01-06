package pl.hotel.tobiczyk.controller;

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
class RoomController {
    private static final String ROOM_TYPES = "roomTypes";
    private static final String ROOMS = "rooms";
    private static final String ERRORS = "errors";

    private RoomService roomService;
    private PhotoService photoService;
    private BookedDayService bookedDayService;

    public RoomController(final RoomService roomService, final PhotoService photoService,
                          final BookedDayService bookedDayService) {
        this.roomService = roomService;
        this.photoService = photoService;
        this.bookedDayService = bookedDayService;
    }

    @ModelAttribute("rooms")
    List<Room> readAllRooms() {
        return roomService.findAllRooms();
    }

    @GetMapping(path = "/rooms")
    public String showAllRooms(final Model model) {
        model.addAttribute(ROOM_TYPES, roomService.findAllRoomTypes());
        model.addAttribute("photos", photoService.showAllPhotos());
        return ROOMS;
    }


    @GetMapping("/panel/admin/addRoom")
    public String addRoomForm(final Model model) {
        model.addAttribute("roomWriteModel", new RoomWriteModel());
        model.addAttribute(ROOM_TYPES, roomService.findAllRoomTypes());
        return "addRoom";
    }

    @PostMapping("/panel/admin/addRoom")
    public String addRoom(@ModelAttribute @Valid final RoomWriteModel roomWriteModel,
                          final BindingResult result, final Model model) {
        if(result.hasErrors()) {
            model.addAttribute(ERRORS, result.getAllErrors());
            return addRoomForm(model);
        }
        roomService.createNewRoom(roomWriteModel);
        return "addRoom";
    }


    @GetMapping("/panel/admin/editRooms")
    public String editRooms(final Model model) {
        model.addAttribute(ROOMS, roomService.findAllRooms());
        return "editRooms";
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
        return "changeRoomPrice";
    }

    @PostMapping("/panel/admin/changeRoomPrice")
    public String changeRoomPrice(@ModelAttribute @Valid final ChangePriceDto changePriceDto,
                                  final BindingResult result, final Model model) {
        if(result.hasErrors()) {
            model.addAttribute(ERRORS, result.getAllErrors());
            return changeRoomPriceForm(model);
        }
        roomService.updateRoomTypePrice(changePriceDto);
        return "changeRoomPrice";
    }

    @GetMapping("panel/admin/blockRoom")
    public String blockRoomForm(final Model model) {
        model.addAttribute("blockRoomDto", new BlockRoomDto());
        model.addAttribute(ROOMS, roomService.findAllRooms());
        model.addAttribute("ranges", bookedDayService.getBlockedDaysForAllRooms());
        return "blockRoom";
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
