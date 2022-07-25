package pl.hotel.tobiczyk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.hotel.tobiczyk.domain.model.Photo;
import pl.hotel.tobiczyk.service.PhotoService;
import pl.hotel.tobiczyk.service.RoomService;

import java.io.IOException;

@Controller
public class PhotoController {
    private RoomService roomService;
    private PhotoService photoService;

    public PhotoController(RoomService roomService, PhotoService photoService) {
        this.roomService = roomService;
        this.photoService = photoService;
    }

    @GetMapping("panel/admin/uploadPhoto")
    public String uploadPhotoForm(Model model) {
        model.addAttribute("photo", new Photo());
        model.addAttribute("roomTypes", roomService.findAllRoomTypes());
        return "uploadPhoto";
    }

    @PostMapping("panel/admin/uploadPhoto")
    public String uploadPhoto(@RequestParam("id") Long id, @RequestParam("file") MultipartFile file,
                              Model model) throws IOException {
        if(file.isEmpty()){
            model.addAttribute("error", "Plik nie może być pusty!");
            return uploadPhotoForm(model);
        }
        photoService.upload(file, id);
        return "uploadPhoto";
    }
}
