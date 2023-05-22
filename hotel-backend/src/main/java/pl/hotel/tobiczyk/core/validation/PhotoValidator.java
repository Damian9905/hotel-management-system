package pl.hotel.tobiczyk.core.validation;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.hotel.tobiczyk.common.constants.TemplateConstants;
import pl.hotel.tobiczyk.core.exception.EmptyPhotoException;
import pl.hotel.tobiczyk.core.exception.UnsupportedFileTypeException;

import java.util.List;

@Component
@NoArgsConstructor
public class PhotoValidator  {

    private static final List<String> VALID_FILE_TYPES  = List.of(
            "image/jpg", "image/jpeg", "image/png", "image/bmp"

    );

    public void validate(final MultipartFile file) {
        if(file.isEmpty())
            throw new EmptyPhotoException(TemplateConstants.PHOTO_TEMPLATE);

        if(!VALID_FILE_TYPES.contains(file.getContentType()))
            throw new UnsupportedFileTypeException(TemplateConstants.PHOTO_TEMPLATE);
    }
}
