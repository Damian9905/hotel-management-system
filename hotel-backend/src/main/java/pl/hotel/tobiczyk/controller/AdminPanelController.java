package pl.hotel.tobiczyk.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.hotel.tobiczyk.service.UserService;

@Controller
@RequestMapping(path="/panel/admin")
public class AdminPanelController {
    private UserService userService;

    public AdminPanelController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAdminPanel(@AuthenticationPrincipal OidcUser oidcUser) {
        return "adminPanel/adminPanel";
    }

    @GetMapping("/users")
    public String getUsers(final Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "adminPanel/users";
    }
}
