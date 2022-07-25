package pl.hotel.tobiczyk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.hotel.tobiczyk.domain.dto.ChangePriceDto;
import pl.hotel.tobiczyk.domain.dto.RoomDto;
import pl.hotel.tobiczyk.domain.model.Room;
import pl.hotel.tobiczyk.service.PhotoService;
import pl.hotel.tobiczyk.service.RoomService;

import javax.validation.Valid;
import java.util.List;

@Controller
class RoomController {
    RoomService roomService;
    PhotoService photoService;

    public RoomController(final RoomService roomService, final PhotoService photoService) {
        this.roomService = roomService;
        this.photoService = photoService;
    }

    @ModelAttribute("rooms")
    List<Room> readAllRooms() {
        return roomService.findAllRooms();
    }


    @GetMapping(path = "/rooms")
    public String showAllRooms(Model model) {
        model.addAttribute("roomTypes", roomService.findAllRoomTypes());
        model.addAttribute("photos", photoService.showAllPhotos());
        return "rooms";
    }


    @GetMapping("/panel/admin/addRoom")
    public String addRoomForm(Model model) {
        model.addAttribute("roomDto", new RoomDto());
        model.addAttribute("roomTypes", roomService.findAllRoomTypes());
        return "addRoom";
    }

    @PostMapping("/panel/admin/addRoom")
    public String addRoom(@ModelAttribute @Valid RoomDto roomDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return addRoomForm(model);
        }
        roomService.createNewRoom(roomDto);
        return "addRoom";
    }


    @GetMapping("/panel/admin/changeRoomPrice")
    public String changeRoomPriceForm(Model model) {
        model.addAttribute("changePriceDto", new ChangePriceDto());
        model.addAttribute("roomTypes", roomService.findAllRoomTypes());
        return "changeRoomPrice";
    }

    @PostMapping("/panel/admin/changeRoomPrice")
    public String changeRoomPrice(@ModelAttribute @Valid ChangePriceDto changePriceDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return changeRoomPriceForm(model);
        }
        roomService.updateRoomTypePrice(changePriceDto);
        return "changeRoomPrice";
    }
}
