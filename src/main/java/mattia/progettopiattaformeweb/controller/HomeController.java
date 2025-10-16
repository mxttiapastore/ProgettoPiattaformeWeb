package mattia.progettopiattaformeweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("msg", "Hello Spring MVC + JSP");
        return "home"; // risolve /WEB-INF/jsp/home.jsp
    }
}
