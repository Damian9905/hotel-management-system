package pl.hotel.tobiczyk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.hotel.tobiczyk.domain.model.PaymentMethod;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public record ReservationWriteView(
    Long id,
    Long roomId,
    String customerId,
    String roomName,
    Integer numberOfPeople,
    String dateFrom,
    String dateTo,
    PaymentMethod paymentMethod,
    String totalValue
) {
}
