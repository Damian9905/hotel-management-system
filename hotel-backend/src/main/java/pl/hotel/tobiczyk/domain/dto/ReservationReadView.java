package pl.hotel.tobiczyk.domain.dto;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ReservationReadView(
    Long id,
    String roomName,
    String customerFirstName,
    String customerLastName,
    Integer numberOfPeople,
    LocalDate dateFrom,
    LocalDate dateTo,
    String paymentMethod,
    Double totalValue
) {

}
