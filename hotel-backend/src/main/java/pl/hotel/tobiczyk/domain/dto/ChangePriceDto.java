package pl.hotel.tobiczyk.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ChangePriceDto {
    @NotNull
    private Long id;
    @NotNull(message = "Price must not be empty!")
    @Positive(message = "Price has to be a positive number! (unless you want to pay ;)")
    private Integer price;
}
