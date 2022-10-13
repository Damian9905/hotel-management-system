package pl.hotel.tobiczyk.domain.exception;

import lombok.Getter;

@Getter
public abstract class PhotoException extends IllegalArgumentException {
  public String template;

  public PhotoException (String message) {
    super(message);
  }
}
