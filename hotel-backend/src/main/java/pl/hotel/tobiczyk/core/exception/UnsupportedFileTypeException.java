package pl.hotel.tobiczyk.core.exception;

import lombok.Value;

@Value
public class UnsupportedFileTypeException extends PhotoException {
    private static final String MESSAGE = "Unsupported file type!";
    String template;

    public UnsupportedFileTypeException(final String template) {
        super(MESSAGE);
        this.template = template;
    }
}
