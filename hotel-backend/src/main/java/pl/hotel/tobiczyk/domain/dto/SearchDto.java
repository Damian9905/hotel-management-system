package pl.hotel.tobiczyk.domain.dto;

import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
public record SearchDto(
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dateFrom,
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dateTo
) {
}
