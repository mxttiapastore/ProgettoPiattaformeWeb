package mattia.progettopiattaformeweb.dto;

import java.math.BigDecimal;

public record VoceCarrelloDto(
        Long id,
        Long componenteId,
        String nomeComponente,
        Integer quantita,
        BigDecimal prezzoUnitario,
        BigDecimal subtotale
) {}
