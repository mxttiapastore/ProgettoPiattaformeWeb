package mattia.progettopiattaformeweb.controller;

import mattia.progettopiattaformeweb.dto.ProfiloUtenteDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UtenteController {

    @GetMapping("/me")
    public ProfiloUtenteDto me(Authentication authentication) {
        var roles = authentication.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .toList();
        return new ProfiloUtenteDto(authentication.getName(), roles);
    }
}
