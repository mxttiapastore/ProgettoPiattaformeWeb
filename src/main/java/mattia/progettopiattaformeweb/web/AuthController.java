package mattia.progettopiattaformeweb.web;

import mattia.progettopiattaformeweb.dto.*;
import mattia.progettopiattaformeweb.security.JwtUtils;
import mattia.progettopiattaformeweb.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtils jwt;
    private final AuthService authService;

    public AuthController(AuthenticationManager authManager, JwtUtils jwt, AuthService authService) {
        this.authManager = authManager;
        this.jwt = jwt;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password())
        );
        UserDetails user = (UserDetails) auth.getPrincipal();
        String token = jwt.generateToken(user);
        return ResponseEntity.ok(new JwtResponse(token, 3600));
    }
}
