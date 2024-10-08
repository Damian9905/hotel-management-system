package pl.hotel.tobiczyk.service;

import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Clients;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.UserApi;
import org.openapitools.client.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import pl.hotel.tobiczyk.domain.dto.UserDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
  @Value("${okta.oAuth2.orgUrl}")
  private static String orgUrl;
  @Value("${okta.oAuth2.clientCredentialsSecret}")
  private static String clientCredentialsSecret;

  private final UserApi userApi;
  private final ApiClient client;

  public UserService() {
    client = Clients.builder()
        .setOrgUrl(orgUrl)
        .setClientCredentials(new TokenClientCredentials(clientCredentialsSecret))
        .build();
    userApi = new UserApi(client);
  }

  public UserProfile getUserProfile(final String userId) {
    return userApi.getUser(userId).getProfile();
  }

  public List<UserDto> getAllUsers() {
    return userApi.listUsers(null, null, 50, null, null, null, null)
        .stream().map(user -> UserDto.builder()
            .name(Objects.requireNonNull(user.getProfile()).getFirstName())
            .lastName(user.getProfile().getLastName())
            .email(user.getProfile().getEmail())
            .build())
        .collect(Collectors.toList());
  }

  public UserDto getUserDto(OidcUser user) {
    final var userInfo = user.getUserInfo();
    return UserDto.builder()
        .id(user.getName())
        .name(userInfo.getGivenName())
        .lastName(userInfo.getFamilyName())
        .email(userInfo.getEmail())
        .build();
  }

  public void changePassword(final String userId, final String oldPassword, String newPassword) {
    var request = new ChangePasswordRequest();
    request.setOldPassword(new PasswordCredential().value(oldPassword));
    request.setNewPassword(new PasswordCredential().value(newPassword));
    userApi.changePassword(userId, request, false);
  }
}
