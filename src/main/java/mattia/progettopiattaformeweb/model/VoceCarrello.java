package mattia.progettopiattaformeweb.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "voce_carrello")
public class VoceCarrello {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "carrello_id")
    private Carrello carrello;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "componente_id")
    private Componente componente;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantita = 1;
}
