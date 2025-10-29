package mattia.progettopiattaformeweb.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import mattia.progettopiattaformeweb.model.TipologiaComponente;

import java.math.BigDecimal;

public record NuovoComponenteRequest(
        @NotNull TipologiaComponente tipologia,
        @NotBlank @Size(max = 255) String nome,
        @NotBlank @Size(max = 100) String marca,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal prezzo,
        @Size(max = 50) String socketCpu,
        @Size(max = 50) String chipsetScheda,
        @Size(max = 50) String fattoreForma,
        @Size(max = 20) String tipoMemoria,
        Integer wattaggio
) {}
