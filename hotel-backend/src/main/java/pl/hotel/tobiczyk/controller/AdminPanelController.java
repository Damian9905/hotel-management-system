package pl.hotel.tobiczyk.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/panel/admin")
public class AdminPanelController {

    public AdminPanelController() {}

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public String showAdminPanel(@AuthenticationPrincipal OidcUser oidcUser) {
        System.out.println(oidcUser);
        return "adminPanel";
    }
}
