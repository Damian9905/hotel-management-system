package pl.hotel.tobiczyk.core.exception;

import lombok.Value;

public class UnsupportedFileTypeException extends PhotoException {
    private static final String MESSAGE = "Unsupported file type!";

    public UnsupportedFileTypeException(final String template) {
        super(MESSAGE, template);
    }
}
