package pl.hotel.tobiczyk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.hotel.tobiczyk.domain.dto.ChangePriceDto;
import pl.hotel.tobiczyk.domain.dto.RoomDto;
import pl.hotel.tobiczyk.domain.model.Photo;
import pl.hotel.tobiczyk.repository.RoomTypeRepository;
import pl.hotel.tobiczyk.service.PhotoService;
import pl.hotel.tobiczyk.service.RoomService;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping(path="/panel/admin")
public class AdminPanelController {
    private RoomService roomService;
    private RoomTypeRepository roomTypeRepository;
    private PhotoService photoService;

    public AdminPanelController(RoomService roomService, RoomTypeRepository roomTypeRepository, PhotoService photoService) {
        this.roomService = roomService;
        this.roomTypeRepository = roomTypeRepository;
        this.photoService = photoService;
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

    @GetMapping("/uploadPhoto")
    public String uploadPhotoForm(Model model) {
        model.addAttribute("photo", new Photo());
        model.addAttribute("roomTypes", roomTypeRepository.findAll());
        return "uploadPhoto";
    }

    @PostMapping("/uploadPhoto")
    public String uploadPhoto(@RequestParam("id") Long id, @RequestParam("file") MultipartFile file) throws IOException {

        photoService.upload(file, id);
        return "uploadPhoto";
    }
}
