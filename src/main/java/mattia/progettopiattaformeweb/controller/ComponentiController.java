package mattia.progettopiattaformeweb.controller;

import jakarta.validation.Valid;
import mattia.progettopiattaformeweb.dto.ComponenteDto;
import mattia.progettopiattaformeweb.dto.NuovoComponenteRequest;
import mattia.progettopiattaformeweb.model.TipologiaComponente;
import mattia.progettopiattaformeweb.service.ComponenteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/componenti")
public class ComponentiController {

    private final ComponenteService servizio;

    public ComponentiController(ComponenteService servizio) { this.servizio = servizio; }

    @GetMapping
    public Page<ComponenteDto> elenco(@RequestParam TipologiaComponente tipologia,
                                      @RequestParam(required = false) String marca,
                                      @PageableDefault(size = 12, sort = "prezzo") Pageable p) {
        return servizio.cerca(tipologia, marca, p);
    }

    @GetMapping("/{id}")
    public ComponenteDto dettaglio(@PathVariable Long id) { return servizio.dettaglio(id); }

    @GetMapping("/tipologie")
    public TipologiaComponente[] tipologie() {
        return servizio.elencoTipologie();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ComponenteDto crea(@Valid @RequestBody NuovoComponenteRequest req) {
        return servizio.crea(req);
    }
}
