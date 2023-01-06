package pl.hotel.tobiczyk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
  private String id;
  private String name;
  private String lastName;
  private String email;
}
