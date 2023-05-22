package pl.hotel.tobiczyk.core.exception;

import lombok.Value;

@Value
public class DayAlreadyBookedException extends DateException {
  static String MESSAGE = "Day(s) Already Booked!";
  String template;

  public DayAlreadyBookedException(final String template) {
    super(MESSAGE);
    this.template = template;
  }
}
