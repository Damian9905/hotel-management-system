package pl.hotel.tobiczyk.domain.exception;

import lombok.Value;

@Value
public class InvalidDateRangeException extends DateException {
  static String MESSAGE = "Start date must be before end date";
  String template;

  public InvalidDateRangeException(final String template) {
    super(MESSAGE);
    this.template = template;
  }
}
