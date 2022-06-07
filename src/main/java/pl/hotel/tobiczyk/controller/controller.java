package pl.hotel.tobiczyk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.hotel.tobiczyk.domain.model.Room;
import pl.hotel.tobiczyk.domain.model.RoomType;
import pl.hotel.tobiczyk.repository.RoomTypeRepository;
import pl.hotel.tobiczyk.service.RoomService;

import java.util.List;

@RestController
@RequestMapping(path = "/rooms")
class RoomController {
    RoomService roomService;
    RoomTypeRepository roomTypeRepository;

    @Autowired
    public RoomController(final RoomService roomService, final RoomTypeRepository roomTypeRepository) {
        this.roomService = roomService;
        this.roomTypeRepository = roomTypeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Room>> showRooms(){
        return ResponseEntity.ok(roomService.findAllRooms());
    }

    @GetMapping(path = "/types")
    public ResponseEntity<List<RoomType>> showTypes() {
        return ResponseEntity.ok(roomTypeRepository.findAll());
    }
}
