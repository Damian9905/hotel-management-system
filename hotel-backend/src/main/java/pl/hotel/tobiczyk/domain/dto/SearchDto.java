package pl.hotel.tobiczyk.domain.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class SearchDto {
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateFrom;
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateTo;
}
