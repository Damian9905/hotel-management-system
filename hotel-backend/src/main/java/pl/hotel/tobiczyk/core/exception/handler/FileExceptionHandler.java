package pl.hotel.tobiczyk.core.exception.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import pl.hotel.tobiczyk.common.constants.TemplateConstants;
import pl.hotel.tobiczyk.core.exception.EmptyPhotoException;
import pl.hotel.tobiczyk.core.exception.PhotoException;
import pl.hotel.tobiczyk.core.exception.UnsupportedFileTypeException;
import pl.hotel.tobiczyk.service.RoomService;

@ControllerAdvice
public class FileExceptionHandler {
    private RoomService roomService;

    public FileExceptionHandler(final RoomService roomService) {
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
