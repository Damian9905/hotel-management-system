package pl.hotel.tobiczyk.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record RoomWriteModel(
    @NotBlank(message = "Name must not be blank!")
    String name,
    @NotBlank(message = "Description must not be blank!")
    String description,
    @NotNull(message = "You must choose room type!")
    Long roomTypeId
) {
}
