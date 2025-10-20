package mattia.progettopiattaformeweb.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    // endpoint libero per verificare che l'app risponde
    @GetMapping("/open")
    public Map<String, String> open() {
        return Map.of("ok", "true");
    }

    // endpoint protetto: richiede Bearer token valido
    @GetMapping("/secure")
    public Map<String, Object> secure(Authentication auth) {
        return Map.of(
                "user", auth.getName(),
                "authorities", auth.getAuthorities()
        );
    }
}
