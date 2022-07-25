package pl.hotel.tobiczyk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/panel/admin")
public class AdminPanelController {

    public AdminPanelController() {}

    @GetMapping
    public String showAdminPanel() {
        return "adminPanel";
    }
}
