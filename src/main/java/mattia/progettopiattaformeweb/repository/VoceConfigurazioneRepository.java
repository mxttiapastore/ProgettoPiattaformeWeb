package mattia.progettopiattaformeweb.repository;

import mattia.progettopiattaformeweb.model.VoceConfigurazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoceConfigurazioneRepository extends JpaRepository<VoceConfigurazione, Long> {
    List<VoceConfigurazione> findByConfigurazioneId(Long configurazioneId);
}
