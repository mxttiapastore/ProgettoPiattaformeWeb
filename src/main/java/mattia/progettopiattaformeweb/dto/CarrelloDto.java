package mattia.progettopiattaformeweb.dto;

import java.math.BigDecimal;
import java.util.List;

public record CarrelloDto(
        Long id,
        List<VoceCarrelloDto> voci,
        BigDecimal totale
) {}
