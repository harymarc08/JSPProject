package mg.neocorporation.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/clients")
    public String viewClients() {
        return "client-list";
    }
}
