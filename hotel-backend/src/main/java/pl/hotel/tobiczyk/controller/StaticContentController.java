package pl.hotel.tobiczyk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticContentController {

    @GetMapping("/")
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/contact")
    public String getContactPage() {
        return "staticContent/contact";
    }

    @GetMapping("/login1")
    public String loginPage() {
        return "redirect:/";
    }
}
