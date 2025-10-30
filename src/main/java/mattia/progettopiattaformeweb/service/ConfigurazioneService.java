package mattia.progettopiattaformeweb.service;

import mattia.progettopiattaformeweb.dto.*;
import mattia.progettopiattaformeweb.model.*;
import mattia.progettopiattaformeweb.repository.ComponenteRepository;
import mattia.progettopiattaformeweb.repository.ConfigurazioneRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
public class ConfigurazioneService {

    private final ConfigurazioneRepository confRepo;
    private final ComponenteRepository compRepo;

    public ConfigurazioneService(ConfigurazioneRepository confRepo, ComponenteRepository compRepo) {
        this.confRepo = confRepo; this.compRepo = compRepo;
    }

    @Transactional
    public ConfigurazioneDto crea(String nome) {
        var c = Configurazione.builder().nome(nome).build();
        return toDto(confRepo.save(c));
    }

    @Transactional
    public ConfigurazioneDto aggiungiVoce(Long configurazioneId, Long componenteId, Integer quantita) {
        var conf = confRepo.findById(configurazioneId).orElseThrow();
        var comp = compRepo.findById(componenteId).orElseThrow();

        // TODO: qui puoi inserire regole di compatibilità (socket, memoria, fattore forma, wattaggio, ecc.)

        var voce = VoceConfigurazione.builder()
                .configurazione(conf)
                .componente(comp)
                .quantita(quantita == null ? 1 : quantita)
                .build();
        conf.getVoci().add(voce);
        return toDto(conf);
    }

    @Transactional(readOnly = true)
    public ConfigurazioneDto dettaglio(Long id) {
        return toDto(confRepo.findById(id).orElseThrow());
    }

    @Transactional(readOnly = true)
    public List<ConfigurazioneDto> elenco() {
        return confRepo.findAll(Sort.by(Sort.Direction.ASC, "nome"))
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public void elimina(Long id) {
        var configurazione = confRepo.findById(id).orElseThrow();
        confRepo.delete(configurazione);
    }

    private ConfigurazioneDto toDto(Configurazione c) {
        var voci = c.getVoci().stream().map(v -> new VoceConfigurazioneDto(
                v.getId(),
                v.getComponente().getId(),
                v.getComponente().getNome(),
                v.getComponente().getTipologia(),
                v.getQuantita(),
                v.getComponente().getPrezzo()
        )).collect(Collectors.toList());

        var totale = voci.stream()
                .map(v -> v.prezzoUnitario().multiply(BigDecimal.valueOf(v.quantita())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ConfigurazioneDto(c.getId(), c.getNome(), voci, totale);
    }
}
