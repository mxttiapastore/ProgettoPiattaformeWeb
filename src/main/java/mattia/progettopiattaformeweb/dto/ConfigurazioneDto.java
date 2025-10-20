package mattia.progettopiattaformeweb.dto;

import java.math.BigDecimal;
import java.util.List;

public record ConfigurazioneDto(
        Long id,
        String nome,
        List<VoceConfigurazioneDto> voci,
        BigDecimal totale
) {}
