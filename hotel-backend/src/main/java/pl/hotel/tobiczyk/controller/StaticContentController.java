package pl.hotel.tobiczyk.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticContentController {

    @GetMapping("/")
    public String getHomePage(@AuthenticationPrincipal OidcUser oidcUser) {
        return "index";
    }

    @GetMapping("/contact")
    public String getContactPage() {
        return "contact";
    }

    @GetMapping("/login1")
    public String loginPage() {
        return "redirect:/";
    }
}
