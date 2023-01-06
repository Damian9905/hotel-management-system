package pl.hotel.tobiczyk.domain.model;

public enum PaymentStatus {
  COMPLETED("Opłacona"),
  UNCOMPLETED("Nieopłacona");

  private String value;

  PaymentStatus(final String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
