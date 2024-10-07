package pl.hotel.tobiczyk.core.exception;

import lombok.Getter;

@Getter
public abstract class PhotoException extends IllegalArgumentException {
  final String template;

  protected PhotoException (String message, String template) {
    super(message);
    this.template = template;
  }
}
