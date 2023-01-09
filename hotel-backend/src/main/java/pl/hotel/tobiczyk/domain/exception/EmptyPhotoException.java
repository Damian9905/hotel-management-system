package pl.hotel.tobiczyk.domain.exception;

import lombok.Value;

@Value
public class EmptyPhotoException extends PhotoException {
    private static final String MESSAGE = "File cannot be empty!";
    String template;

    public EmptyPhotoException(final String template) {
        super(MESSAGE);
        this.template = template;

    }
}
