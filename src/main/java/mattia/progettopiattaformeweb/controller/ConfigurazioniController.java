package mattia.progettopiattaformeweb.controller;

import jakarta.validation.Valid;
import mattia.progettopiattaformeweb.dto.*;
import mattia.progettopiattaformeweb.service.ConfigurazioneService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configurazioni")
public class ConfigurazioniController {

    private final ConfigurazioneService servizio;

    public ConfigurazioniController(ConfigurazioneService servizio) { this.servizio = servizio; }

    @PostMapping
    public ConfigurazioneDto crea(@Valid @RequestBody CreaConfigurazioneReq req) {
        return servizio.crea(req.nome());
    }

    @GetMapping
    public List<ConfigurazioneDto> elenco() {
        return servizio.elenco();
    }

    @GetMapping("/{id}")
    public ConfigurazioneDto dettaglio(@PathVariable Long id) { return servizio.dettaglio(id); }

    @PutMapping("/{id}/voci")
    public ConfigurazioneDto aggiungi(@PathVariable Long id, @Valid @RequestBody AggiungiVoceReq req) {
        return servizio.aggiungiVoce(id, req.componenteId(), req.quantita());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void elimina(@PathVariable Long id) {
        servizio.elimina(id);
    }
}
