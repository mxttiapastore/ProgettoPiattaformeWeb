package mattia.progettopiattaformeweb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AggiungiComponenteCarrelloReq(@NotNull Long componenteId, @Min(1) Integer quantita) {}
