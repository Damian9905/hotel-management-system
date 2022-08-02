package pl.hotel.tobiczyk.validation;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import pl.hotel.tobiczyk.domain.exception.EmptyPhotoException;
import pl.hotel.tobiczyk.domain.exception.UnsupportedFileTypeException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PhotoValidatorTest {

    @Test
    void emptyPhotoRequest_shouldThrowEmptyPhotoException() {
        var validator = new PhotoValidator();
        var file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(true);

        assertThrows(EmptyPhotoException.class, () -> validator.validate(file));
    }

    @Test
    void unsupportedFileRequest_shouldThrowUnsupportedFileTypeException() {
        var validator = new PhotoValidator();
        var file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn("application/pdf");

        assertThrows(UnsupportedFileTypeException.class, () -> validator.validate(file));
    }

    @Test
    void supportedFileRequest_shouldNotThrowUnsupportedFileTypeException() {
        var validator = new PhotoValidator();
        var file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn("image/jpeg");

        assertDoesNotThrow(() -> validator.validate(file));
    }
}