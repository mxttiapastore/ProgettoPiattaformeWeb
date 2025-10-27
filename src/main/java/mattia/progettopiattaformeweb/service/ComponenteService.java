package mattia.progettopiattaformeweb.service;

import mattia.progettopiattaformeweb.dto.ComponenteDto;
import mattia.progettopiattaformeweb.model.Componente;
import mattia.progettopiattaformeweb.model.TipologiaComponente;
import mattia.progettopiattaformeweb.repository.ComponenteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    private ComponenteDto toDto(Componente c) {
        return new ComponenteDto(
                c.getId(), c.getTipologia(), c.getNome(), c.getMarca(),
                c.getPrezzo(), c.getSocketCpu(), c.getChipsetScheda(),
                c.getFattoreForma(), c.getTipoMemoria(), c.getWattaggio()
        );
    }
    public List<ComponenteDto> tutti() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

}
