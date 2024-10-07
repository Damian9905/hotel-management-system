package pl.hotel.tobiczyk.domain.dto;

import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor(force = true)
public record BlockRoomDto(
    @NotNull(message = "Room ID can not be empty!")
    Long roomId,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please provide valid start date!")
    LocalDate dateFrom,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please provide valid end date!")
    LocalDate dateTo
) {
}
