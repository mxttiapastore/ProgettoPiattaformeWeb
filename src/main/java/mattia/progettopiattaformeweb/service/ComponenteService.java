package mattia.progettopiattaformeweb.service;

import mattia.progettopiattaformeweb.dto.ComponenteDto;
import mattia.progettopiattaformeweb.dto.NuovoComponenteRequest;
import mattia.progettopiattaformeweb.model.Componente;
import mattia.progettopiattaformeweb.model.TipologiaComponente;
import mattia.progettopiattaformeweb.repository.ComponenteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ComponenteService {

    private final ComponenteRepository repo;

    public ComponenteService(ComponenteRepository repo) { this.repo = repo; }

    @Transactional(readOnly = true)
    public Page<ComponenteDto> cerca(TipologiaComponente tipologia, String marca, Pageable p) {
        Page<Componente> page = (marca == null || marca.isBlank())
                ? repo.findByTipologia(tipologia, p)
                : repo.findByTipologiaAndMarcaContainingIgnoreCase(tipologia, marca, p);
        return page.map(this::toDto);
    }

    @Transactional(readOnly = true)
    public ComponenteDto dettaglio(Long id) {
        return toDto(repo.findById(id).orElseThrow());
    }

    @Transactional
    public ComponenteDto crea(NuovoComponenteRequest req) {
        boolean exists = repo.existsByNomeIgnoreCaseAndMarcaIgnoreCaseAndTipologia(
                req.nome(), req.marca(), req.tipologia()
        );
        if (exists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Componente già presente per questa tipologia e marca");
        }

        Componente componente = Componente.builder()
                .tipologia(req.tipologia())
                .nome(req.nome())
                .marca(req.marca())
                .prezzo(req.prezzo())
                .socketCpu(req.socketCpu())
                .chipsetScheda(req.chipsetScheda())
                .fattoreForma(req.fattoreForma())
                .tipoMemoria(req.tipoMemoria())
                .wattaggio(req.wattaggio())
                .build();

        return toDto(repo.save(componente));
    }

    @Transactional(readOnly = true)
    public TipologiaComponente[] elencoTipologie() {
        return TipologiaComponente.values();
    }

    private ComponenteDto toDto(Componente c) {
        return new ComponenteDto(
                c.getId(), c.getTipologia(), c.getNome(), c.getMarca(),
                c.getPrezzo(), c.getSocketCpu(), c.getChipsetScheda(),
                c.getFattoreForma(), c.getTipoMemoria(), c.getWattaggio()
        );
    }
}
