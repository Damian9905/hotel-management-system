package pl.hotel.tobiczyk.domain.exception;

import lombok.Value;

@Value
public class UnsupportedFileTypeException extends IllegalArgumentException {
    private static final String MESSAGE = "Unsupported file type!";

    public UnsupportedFileTypeException() {
        super(MESSAGE);
    }
}
