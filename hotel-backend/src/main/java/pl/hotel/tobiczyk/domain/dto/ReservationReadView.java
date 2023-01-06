package pl.hotel.tobiczyk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationReadView {
  private Long id;
  private String roomName;
  private String customerFirstName;
  private String customerLastName;
  private Integer numberOfPeople;
  private LocalDate dateFrom;
  private LocalDate dateTo;
  private String paymentMethod;
  private Double totalValue;
}
