package mattia.progettopiattaformeweb.dto;

import mattia.progettopiattaformeweb.model.TipologiaComponente;
import java.math.BigDecimal;

public record VoceConfigurazioneDto(
        Long id,
        Long componenteId,
        String nomeComponente,
        TipologiaComponente tipologia,
        Integer quantita,
        BigDecimal prezzoUnitario
) {}
