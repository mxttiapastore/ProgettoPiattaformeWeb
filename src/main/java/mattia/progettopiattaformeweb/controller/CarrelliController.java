package mattia.progettopiattaformeweb.controller;

import jakarta.validation.Valid;
import mattia.progettopiattaformeweb.dto.*;
import mattia.progettopiattaformeweb.service.CarrelloService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrelli")
public class CarrelliController {

    private final CarrelloService servizio;

    public CarrelliController(CarrelloService servizio) { this.servizio = servizio; }

    @PostMapping
    public CarrelloDto crea() { return servizio.crea(); }

    @GetMapping("/{id}")
    public CarrelloDto dettaglio(@PathVariable Long id) { return servizio.dettaglio(id); }

    @PutMapping("/{id}/componenti")
    public CarrelloDto aggiungiComponente(@PathVariable Long id,
                                          @Valid @RequestBody AggiungiComponenteCarrelloReq req) {
        return servizio.aggiungiComponente(id, req.componenteId(), req.quantita());
    }

    @PutMapping("/{id}/configurazioni/{configId}")
    public CarrelloDto aggiungiConfigurazione(@PathVariable Long id, @PathVariable Long configId) {
        return servizio.aggiungiConfigurazione(id, configId);
    }

    @DeleteMapping("/{id}/voci/{voceId}")
    public CarrelloDto rimuoviVoce(@PathVariable Long id, @PathVariable Long voceId) {
        return servizio.rimuoviVoce(id, voceId);
    }
}
