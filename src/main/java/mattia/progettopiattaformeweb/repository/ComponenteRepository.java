package mattia.progettopiattaformeweb.repository;

import mattia.progettopiattaformeweb.model.Componente;
import mattia.progettopiattaformeweb.model.TipologiaComponente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponenteRepository extends JpaRepository<Componente, Long> {
    Page<Componente> findByTipologia(TipologiaComponente tipologia, Pageable p);
    Page<Componente> findByTipologiaAndMarcaContainingIgnoreCase(TipologiaComponente tipologia, String marca, Pageable p);
    Page<Componente> findByTipologiaAndNomeContainingIgnoreCase(TipologiaComponente tipologia, String nome, Pageable p);
    Page<Componente> findByTipologiaAndMarcaContainingIgnoreCaseAndNomeContainingIgnoreCase(
            TipologiaComponente tipologia, String marca, String nome, Pageable p);
    boolean existsByNomeIgnoreCaseAndMarcaIgnoreCaseAndTipologia(String nome, String marca, TipologiaComponente tipologia);
}
