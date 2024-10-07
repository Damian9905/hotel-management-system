package pl.hotel.tobiczyk.core.exception;

public class EmptyPhotoException extends PhotoException {
    private static final String MESSAGE = "File cannot be empty!";

    public EmptyPhotoException(final String template) {
        super(MESSAGE, template);
    }
}
