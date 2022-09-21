package pl.hotel.tobiczyk.domain.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class BlockRoomDto {

    @NotNull(message = "Room ID can not be empty!")
    private Long roomId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please provide valid start date!")
    private LocalDate dateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please provide valid end date!")
    private LocalDate dateTo;
}
