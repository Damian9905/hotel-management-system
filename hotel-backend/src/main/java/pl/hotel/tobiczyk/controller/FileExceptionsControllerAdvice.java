package pl.hotel.tobiczyk.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import pl.hotel.tobiczyk.domain.exception.EmptyPhotoException;
import pl.hotel.tobiczyk.domain.exception.UnsupportedFileTypeException;
import pl.hotel.tobiczyk.service.RoomService;

@ControllerAdvice
public class FileExceptionsControllerAdvice {
    public static final String TEMPLATE = "uploadPhoto";
    private RoomService roomService;

    public FileExceptionsControllerAdvice(RoomService roomService) {
        this.roomService = roomService;
    }

    @ExceptionHandler(EmptyPhotoException.class)
    public String handleEmptyPhotoException(EmptyPhotoException ex, Model model) {
        model.addAttribute("exception", ex.getMessage());
        model.addAttribute("roomTypes", roomService.findAllRoomTypes());
        return TEMPLATE;
    }

    @ExceptionHandler(UnsupportedFileTypeException.class)
    public String handleEmptyPhotoException(UnsupportedFileTypeException ex, Model model) {
        model.addAttribute("exception", ex.getMessage());
        model.addAttribute("roomTypes", roomService.findAllRoomTypes());
        return TEMPLATE;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, Model model) {
        model.addAttribute("exception", ex.getMessage());
        model.addAttribute("roomTypes", roomService.findAllRoomTypes());
        return TEMPLATE;
    }
}
