package mattia.progettopiattaformeweb.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "configurazione")
public class Configurazione {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Builder.Default
    @OneToMany(mappedBy = "configurazione", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoceConfigurazione> voci = new ArrayList<>();
}
