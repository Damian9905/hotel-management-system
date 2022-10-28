package pl.hotel.tobiczyk.service;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import pl.hotel.tobiczyk.domain.dto.UserDto;

@Service
public class UserService {

  public UserDto getUserDto(OidcUser user) {
    final var userInfo = user.getUserInfo();
    return UserDto.builder()
        .id(user.getName())
        .name(userInfo.getGivenName())
        .lastName(userInfo.getFamilyName())
        .email(userInfo.getEmail())
        .build();
  }
}
