package pl.hotel.tobiczyk.domain.dto;

import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor(force = true)
public record ChangePriceDto(
    @NotNull
    Long id,
    @NotNull(message = "Price must not be empty!")
    @Positive(message = "Price has to be a positive number!")
    Integer price
){

}
