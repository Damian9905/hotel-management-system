package pl.hotel.tobiczyk.controller.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import pl.hotel.tobiczyk.domain.constants.TemplateConstants;
import pl.hotel.tobiczyk.domain.exception.EmptyPhotoException;
import pl.hotel.tobiczyk.domain.exception.PhotoException;
import pl.hotel.tobiczyk.domain.exception.UnsupportedFileTypeException;
import pl.hotel.tobiczyk.service.RoomService;

@ControllerAdvice
public class FileExceptionsControllerAdvice {
    private RoomService roomService;

    public FileExceptionsControllerAdvice(final RoomService roomService) {
        this.roomService = roomService;
    }

    @ExceptionHandler({EmptyPhotoException.class, UnsupportedFileTypeException.class, MaxUploadSizeExceededException.class})
    public String handleEmptyPhotoException(final PhotoException ex, final Model model) {
        if(ex.getTemplate().equals(TemplateConstants.PHOTO_TEMPLATE)){
            model.addAttribute("exception", ex.getMessage());
            model.addAttribute("roomTypes", roomService.findAllRoomTypes());
        }
        return ex.getTemplate();
    }
}
