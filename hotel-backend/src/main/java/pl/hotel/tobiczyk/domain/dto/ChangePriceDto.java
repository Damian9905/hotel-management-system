package pl.hotel.tobiczyk.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangePriceDto {
    @NotNull
    private Long id;
    @NotNull
    private Integer price;
}
