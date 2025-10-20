package mattia.progettopiattaformeweb.service;

import mattia.progettopiattaformeweb.dto.*;
import mattia.progettopiattaformeweb.model.*;
import mattia.progettopiattaformeweb.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
public class CarrelloService {

    private final CarrelloRepository carRepo;
    private final VoceCarrelloRepository voceRepo;
    private final ComponenteRepository compRepo;
    private final ConfigurazioneRepository confRepo;

    public CarrelloService(CarrelloRepository carRepo, VoceCarrelloRepository voceRepo,
                           ComponenteRepository compRepo, ConfigurazioneRepository confRepo) {
        this.carRepo = carRepo; this.voceRepo = voceRepo;
        this.compRepo = compRepo; this.confRepo = confRepo;
    }

    @Transactional
    public CarrelloDto crea() {
        return toDto(carRepo.save(Carrello.builder().build()));
    }

    @Transactional
    public CarrelloDto aggiungiComponente(Long carrelloId, Long componenteId, Integer quantita) {
        var car = carRepo.findById(carrelloId).orElseThrow();
        var comp = compRepo.findById(componenteId).orElseThrow();
        var voce = VoceCarrello.builder()
                .carrello(car).componente(comp)
                .quantita(quantita == null ? 1 : quantita)
                .build();
        car.getVoci().add(voce);
        return toDto(car);
    }

    @Transactional
    public CarrelloDto aggiungiConfigurazione(Long carrelloId, Long configurazioneId) {
        var car = carRepo.findById(carrelloId).orElseThrow();
        var conf = confRepo.findById(configurazioneId).orElseThrow();
        conf.getVoci().forEach(vc -> {
            var voce = VoceCarrello.builder()
                    .carrello(car)
                    .componente(vc.getComponente())
                    .quantita(vc.getQuantita())
                    .build();
            car.getVoci().add(voce);
        });
        return toDto(car);
    }

    @Transactional(readOnly = true)
    public CarrelloDto dettaglio(Long id) {
        return toDto(carRepo.findById(id).orElseThrow());
    }

    private CarrelloDto toDto(Carrello c) {
        var voci = c.getVoci().stream().map(v -> {
            var p = v.getComponente().getPrezzo();
            var sub = p.multiply(BigDecimal.valueOf(v.getQuantita()));
            return new VoceCarrelloDto(v.getId(), v.getComponente().getId(),
                    v.getComponente().getNome(), v.getQuantita(), p, sub);
        }).collect(Collectors.toList());

        var totale = voci.stream().map(VoceCarrelloDto::subtotale)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CarrelloDto(c.getId(), voci, totale);
    }
}
