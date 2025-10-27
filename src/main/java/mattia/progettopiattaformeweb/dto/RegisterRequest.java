package mattia.progettopiattaformeweb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank String username,
        @NotBlank @Size(min = 8, message = "La password deve contenere almeno 8 caratteri") String password
) {}
