package mattia.progettopiattaformeweb.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "componente")
public class Componente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipologiaComponente tipologia;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false, length = 100)
    private String marca;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzo;

    // Campi di compatibilità (usali quando servono in base alla tipologia)
    @Column(name = "socket_cpu", length = 50)
    private String socketCpu;       // es. AM5, LGA1700

    @Column(name = "chipset_scheda", length = 50)
    private String chipsetScheda;   // es. B650, Z790

    @Column(name = "fattore_forma", length = 50)
    private String fattoreForma;    // ATX, mATX, ITX

    @Column(name = "tipo_memoria", length = 20)
    private String tipoMemoria;     // DDR4, DDR5

    private Integer wattaggio;      // per PSU
}
