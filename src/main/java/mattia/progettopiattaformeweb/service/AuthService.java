package mattia.progettopiattaformeweb.service;

import mattia.progettopiattaformeweb.domain.User;
import mattia.progettopiattaformeweb.dto.RegisterRequest;
import mattia.progettopiattaformeweb.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Transactional
    public void register(RegisterRequest req) {
        if (repo.existsByUsername(req.username())) {
            throw new IllegalArgumentException("Username already exists");
        }
        User u = new User();
        u.setUsername(req.username());
        u.setPassword(encoder.encode(req.password()));
        u.getRoles().add("ROLE_USER");
        repo.save(u);
    }
}
