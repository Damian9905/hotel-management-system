package pl.hotel.tobiczyk.domain.dto;
import lombok.Builder;

@Builder
public record UserDto(
    String id,
    String name,
    String lastName,
    String email
) {

}
