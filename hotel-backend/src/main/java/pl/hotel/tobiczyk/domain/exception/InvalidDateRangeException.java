package pl.hotel.tobiczyk.domain.exception;

public class InvalidDateRangeException extends IllegalStateException {
  private static final String MESSAGE = "Start date must be before end date";

  public InvalidDateRangeException() {
    super(MESSAGE);
  }
}
