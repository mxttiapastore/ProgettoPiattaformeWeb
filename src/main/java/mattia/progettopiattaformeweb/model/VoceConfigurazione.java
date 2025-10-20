package mattia.progettopiattaformeweb.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "voce_configurazione",
        uniqueConstraints = @UniqueConstraint(name = "uk_conf_componente",
                columnNames = {"configurazione_id","componente_id"}))
public class VoceConfigurazione {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "configurazione_id")
    private Configurazione configurazione;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "componente_id")
    private Componente componente;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantita = 1;
}
