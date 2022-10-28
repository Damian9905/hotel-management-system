package pl.hotel.tobiczyk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.hotel.tobiczyk.domain.model.PaymentMethod;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationWriteView {
  private Long roomId;
  private String customerId;
  private String roomName;
  private Integer numberOfPeople;
  private String dateFrom;
  private String dateTo;
  private PaymentMethod paymentMethod;
  private String totalValue;
}
