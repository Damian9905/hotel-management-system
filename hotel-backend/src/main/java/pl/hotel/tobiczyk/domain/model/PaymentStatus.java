package pl.hotel.tobiczyk.domain.model;

import lombok.Getter;

@Getter
public enum PaymentStatus {
  COMPLETED("Opłacona"),
  UNCOMPLETED("Nieopłacona");

  private String value;

  PaymentStatus(final String value) {
    this.value = value;
  }
}
