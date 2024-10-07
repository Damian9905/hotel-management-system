package pl.hotel.tobiczyk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.hotel.tobiczyk.service.ReservationService;
import pl.hotel.tobiczyk.service.UserService;

@Controller
@RequestMapping(path="/panel/user")
@RequiredArgsConstructor
public class UserPanelController {
  private final ReservationService reservationService;
  private final UserService userService;

  @GetMapping()
  public String getUserPanel(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
    model.addAttribute("name", oidcUser.getUserInfo().getFullName());
    return "userPanel/userPanel";
  }

  @GetMapping("/userReservations")
  public String getUserReservations(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
    model.addAttribute("reservations", reservationService.getUserReservations(oidcUser.getName()));
    return "userPanel/userReservations";
  }

  @GetMapping("/changePassword")
  public String getChangePasswordForm() {
    return "userPanel/changePassword";
  }

  @PostMapping("/changePassword")
  public String getChangePasswordForm(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, @AuthenticationPrincipal OidcUser user, final Model model) {
    userService.changePassword(user.getName(), oldPassword, newPassword);
    return "userPanel/changePassword";
  }
}
