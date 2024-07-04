package mg.neocorporation.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class Mycontroller {
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Hello, Thymeleaf!");
        return "index"; // Ceci correspond à src/main/resources/templates/index.html
    }
}
