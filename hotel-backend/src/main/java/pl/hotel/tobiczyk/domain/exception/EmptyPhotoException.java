package pl.hotel.tobiczyk.domain.exception;

import lombok.Value;

@Value
public class EmptyPhotoException extends IllegalArgumentException {
    private static final String MESSAGE = "File cannot be empty!";

    public EmptyPhotoException() {
        super(MESSAGE);
    }
}
