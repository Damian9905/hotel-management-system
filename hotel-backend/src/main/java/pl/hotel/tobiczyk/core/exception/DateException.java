package pl.hotel.tobiczyk.core.exception;

import lombok.Getter;

@Getter
public abstract class DateException extends IllegalStateException {
  final String template;

  protected DateException (String message, String template) {
    super(message);
    this.template = template;
  }
}
