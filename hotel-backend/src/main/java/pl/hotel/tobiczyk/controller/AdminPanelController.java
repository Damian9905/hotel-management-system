package pl.hotel.tobiczyk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.hotel.tobiczyk.domain.dto.RoomDto;
import pl.hotel.tobiczyk.domain.model.Room;
import pl.hotel.tobiczyk.repository.RoomTypeRepository;
import pl.hotel.tobiczyk.service.RoomService;

import javax.validation.Valid;
import java.net.URI;

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
    public String addRoom(@ModelAttribute @Valid RoomDto roomDto, Model model) {
        Room created = roomService.createNewRoom(roomDto);
        URI location = URI.create(String.format("/rooms/%s", created.getId()));
        String result = "Dodano nowy pokój";
        model.addAttribute("result", result);
        return "addRoom";
    }
}
