package pl.hotel.tobiczyk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.hotel.tobiczyk.domain.dto.ChangePriceDto;
import pl.hotel.tobiczyk.domain.dto.RoomDto;
import pl.hotel.tobiczyk.domain.model.RoomType;
import pl.hotel.tobiczyk.repository.RoomTypeRepository;
import pl.hotel.tobiczyk.service.RoomService;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@RequestMapping(path="/panel/admin")
public class AdminPanelController {
    private RoomService roomService;
    private RoomTypeRepository roomTypeRepository;

    public AdminPanelController(RoomService roomService, RoomTypeRepository roomTypeRepository) {
        this.roomService = roomService;
        this.roomTypeRepository = roomTypeRepository;
    }

    @GetMapping
    public String showAdminPanel() {
        return "adminPanel";
    }

    @GetMapping("/addRoom")
    public String addRoomForm(Model model) {
        model.addAttribute("roomDto", new RoomDto());
        model.addAttribute("roomTypes", roomTypeRepository.findAll());
        return "addRoom";
    }

    @PostMapping("/addRoom")
    public String addRoom(@ModelAttribute @Valid RoomDto roomDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return addRoomForm(model);
        }
        roomService.createNewRoom(roomDto);
        return "addRoom";
    }


    @GetMapping("/changeRoomPrice")
    public String changeRoomPriceForm(Model model) {
        model.addAttribute("changePriceDto", new ChangePriceDto());
        model.addAttribute("roomTypes", roomTypeRepository.findAll());
        return "changeRoomPrice";
    }

    @PostMapping("/changeRoomPrice")
    public String changeRoomPrice(@ModelAttribute @Valid ChangePriceDto changePriceDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return changeRoomPriceForm(model);
        }
        roomService.updateRoomTypePrice(changePriceDto);
        return "changeRoomPrice";
    }
}
