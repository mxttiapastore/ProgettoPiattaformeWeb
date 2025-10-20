package mattia.progettopiattaformeweb.dto;

import mattia.progettopiattaformeweb.model.TipologiaComponente;
import java.math.BigDecimal;

public record ComponenteDto(
        Long id,
        TipologiaComponente tipologia,
        String nome,
        String marca,
        BigDecimal prezzo,
        String socketCpu,
        String chipsetScheda,
        String fattoreForma,
        String tipoMemoria,
        Integer wattaggio
) {}
