package pl.hotel.tobiczyk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.hotel.tobiczyk.domain.model.Photo;
import pl.hotel.tobiczyk.domain.model.RoomType;
import pl.hotel.tobiczyk.service.PhotoService;
import pl.hotel.tobiczyk.service.RoomService;
import pl.hotel.tobiczyk.core.validation.PhotoValidator;

import java.io.IOException;

@Controller
@RequestMapping(path="/panel/admin/photos")
@RequiredArgsConstructor
public class PhotoController {
    private final RoomService roomService;
    private final PhotoService photoService;
    private final PhotoValidator validator;

    @GetMapping("/uploadPhoto")
    public String uploadPhotoForm(final Model model) {
        model.addAttribute("photo", new Photo());
        model.addAttribute("id", new RoomType().getId());
        model.addAttribute("roomTypes", roomService.findAllRoomTypes());
        return "adminPanel/uploadPhoto";
    }

    @PostMapping("/uploadPhoto")
    public String uploadPhoto(@RequestParam("id") final Long id, @RequestParam("file") final MultipartFile file,
                              final Model model) throws IOException {
        validator.validate(file);
        photoService.upload(file, id);
        return uploadPhotoForm(model);
    }

    @GetMapping("/editPhotos")
    public String showPhotos(final Model model){
        model.addAttribute("photos", photoService.showAllPhotos());
        return "adminPanel/editPhotos";
    }

    @PostMapping("/deletePhoto")
    public String deletePhoto(@RequestParam("photoId") final Long id) {
        photoService.deletePhoto(id);
        return "redirect:/panel/admin/editPhotos";
    }
}
