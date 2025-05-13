package projekt.poistenie.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Statistiky {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Celkový počet poistencov
    @Column(nullable = false)
    private long pocetPoistenych;

    // Celkový počet poistných zmlúv
    @Column(nullable = false)
    private long pocetPoisteni;

    // Celkový počet poistných udalostí
    @Column(nullable = false)
    private long pocetUdalosti;
}

