package pl.hotel.tobiczyk.core.exception;

public class InvalidDateRangeException extends DateException {
  static String MESSAGE = "Start date must be before end date";

  public InvalidDateRangeException(final String template) {
    super(MESSAGE, template);
  }
}
