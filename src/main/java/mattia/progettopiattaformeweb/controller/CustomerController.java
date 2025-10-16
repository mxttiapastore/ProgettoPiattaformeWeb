package mattia.progettopiattaformeweb.controller;

import mattia.progettopiattaformeweb.model.Customer;
import mattia.progettopiattaformeweb.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;
    public CustomerController(CustomerService service) { this.service = service; }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("customers", service.all());
        return "customers";
    }

    @PostMapping
    public String create(@RequestParam String firstName, @RequestParam String lastName) {
        Customer c = new Customer();
        c.setFirstName(firstName);
        c.setLastName(lastName);
        service.create(c);
        return "redirect:/customers";
    }
}
