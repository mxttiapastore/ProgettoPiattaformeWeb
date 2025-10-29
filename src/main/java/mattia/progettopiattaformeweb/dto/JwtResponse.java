package mattia.progettopiattaformeweb.dto;

import java.util.List;

public record JwtResponse(String token, long expiresIn, String username, List<String> roles) {}
