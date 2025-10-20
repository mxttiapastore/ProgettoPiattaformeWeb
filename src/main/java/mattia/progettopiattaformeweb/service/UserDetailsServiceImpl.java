package mattia.progettopiattaformeweb.service;

import mattia.progettopiattaformeweb.domain.User;
import mattia.progettopiattaformeweb.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repo;

    public UserDetailsServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Collection<? extends GrantedAuthority> auth =
                u.getRoles().stream().map(SimpleGrantedAuthority::new).toList();
        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), auth);
    }
}
