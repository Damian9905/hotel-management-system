package pl.hotel.tobiczyk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.hotel.tobiczyk.domain.dto.RoomDto;
import pl.hotel.tobiczyk.domain.model.Room;
import pl.hotel.tobiczyk.domain.model.RoomType;
import pl.hotel.tobiczyk.repository.RoomTypeRepository;
import pl.hotel.tobiczyk.service.RoomService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping(path = "/rooms")
class RoomController {
    RoomService roomService;
    RoomTypeRepository roomTypeRepository;


    //API

    @Autowired
    public RoomController(final RoomService roomService, final RoomTypeRepository roomTypeRepository) {
        this.roomService = roomService;
        this.roomTypeRepository = roomTypeRepository;
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<Room>> showRooms(){
        return ResponseEntity.ok(roomService.findAllRooms());
    }

    @GetMapping(path = "/types")
    public ResponseEntity<List<RoomType>> showTypes() {
        return ResponseEntity.ok(roomTypeRepository.findAll());
    }

    @PostMapping(path = "/add")
    @ResponseBody
    public ResponseEntity<Room> addRoom(@RequestBody @Valid RoomDto toCreate) {
        System.out.println("HERE!");
        Room created = roomService.createNewRoom(toCreate);
        URI location = URI.create(String.format("/rooms/%s", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    //TEMPLATES

    @ModelAttribute("rooms")
    List<Room> readAllRooms() {
        return roomService.findAllRooms();
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String showAllRooms(Model model) {
        model.addAttribute("room", new Room());
        return "rooms";
    }
}
