package pl.hotel.tobiczyk.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RoomDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private Long roomTypeId;
}
