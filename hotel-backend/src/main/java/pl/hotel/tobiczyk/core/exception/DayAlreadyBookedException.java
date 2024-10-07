package pl.hotel.tobiczyk.core.exception;

public class DayAlreadyBookedException extends DateException {
  static String MESSAGE = "Day(s) Already Booked!";

  public DayAlreadyBookedException(final String template) {
    super(MESSAGE, template);
  }
}
