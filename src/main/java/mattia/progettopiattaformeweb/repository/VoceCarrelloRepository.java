package mattia.progettopiattaformeweb.repository;

import mattia.progettopiattaformeweb.model.VoceCarrello;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoceCarrelloRepository extends JpaRepository<VoceCarrello, Long> {
    List<VoceCarrello> findByCarrelloId(Long carrelloId);
}
