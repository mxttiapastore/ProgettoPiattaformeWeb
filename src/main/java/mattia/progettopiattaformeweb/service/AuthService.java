package mattia.progettopiattaformeweb.service;

import mattia.progettopiattaformeweb.domain.User;
import mattia.progettopiattaformeweb.dto.JwtResponse;
import mattia.progettopiattaformeweb.dto.RegisterRequest;
import mattia.progettopiattaformeweb.repository.UserRepository;
import mattia.progettopiattaformeweb.security.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public AuthService(UserRepository repo, PasswordEncoder encoder, JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Transactional
    public JwtResponse register(RegisterRequest req) {
        if (repo.existsByUsername(req.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username già in uso");
        }

        User user = new User();
        user.setUsername(req.username());
        user.setPassword(encoder.encode(req.password()));
        user.getRoles().add("ROLE_USER");
        repo.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtUtils.generateToken(userDetails);
        return new JwtResponse(token, jwtUtils.getExpirationSeconds());
    }
}
