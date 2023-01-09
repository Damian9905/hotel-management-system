package pl.hotel.tobiczyk.domain.exception;

import lombok.Getter;

@Getter
public abstract class DateException extends IllegalStateException {
  public String template;

  public DateException (String message) {
    super(message);
  }
}
