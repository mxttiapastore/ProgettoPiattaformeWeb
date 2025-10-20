package mattia.progettopiattaformeweb.dto;

import jakarta.validation.constraints.NotBlank;

public record CreaConfigurazioneReq(@NotBlank String nome) {}
