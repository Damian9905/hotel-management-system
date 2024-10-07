package pl.hotel.tobiczyk.domain.model;

import lombok.Getter;

@Getter
public enum PaymentMethod {
  CASH("Gotówka"),
  CREDIT_CARD_POSTPAID("Karta kredytowa płatność przy zameldowaniu");

  private String value;

  PaymentMethod(final String value) {
    this.value = value;
  }
}
