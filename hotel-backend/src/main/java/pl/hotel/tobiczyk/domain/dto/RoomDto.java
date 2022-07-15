package pl.hotel.tobiczyk.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RoomDto {
    @NotBlank(message = "Name must not be blank!")
    private String name;
    @NotBlank(message = "Description must not be blank!")
    private String description;
    @NotNull(message = "You must choose room type!")
    private Long roomTypeId;
}
