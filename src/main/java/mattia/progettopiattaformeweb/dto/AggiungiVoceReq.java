package mattia.progettopiattaformeweb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AggiungiVoceReq(@NotNull Long componenteId, @Min(1) Integer quantita, String note) {}
