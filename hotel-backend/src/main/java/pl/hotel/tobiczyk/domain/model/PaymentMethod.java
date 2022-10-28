package pl.hotel.tobiczyk.domain.model;

public enum PaymentMethod {
  CASH("Gotówka"),
  CREDIT_CARD_POSTPAID("Karta kredytowa płatność przy zameldowaniu");

  private String value;

  PaymentMethod(final String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
