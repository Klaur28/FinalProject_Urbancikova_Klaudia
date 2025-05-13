package projekt.poistenie.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entita reprezentujúca poistnú udalosť v systéme.
 * Predstavuje perzistentný objekt ukladaný do databázy, ktorý zaznamenáva
 * detaily o poistných udalostiach súvisiacich s poistnými zmluvami.
 *
 * Je mapovaná na tabuľku "udalosti" v databáze.
 */
@Entity
@Table(name = "udalosti")
public class Udalost {

    /**
     * Jedinečný identifikátor poistnej udalosti.
     * Hodnota je generovaná automaticky databázou pri vložení nového záznamu.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Dátum, kedy nastala poistná udalosť.
     * Hodnota nesmie byť null.
     */
    @Column(nullable = false)
    private LocalDate datum;

    /**
     * Textový popis poistnej udalosti, ktorý obsahuje detaily a okolnosti.
     * Maximálna dĺžka je obmedzená na 500 znakov a hodnota nesmie byť null.
     */
    @Column(nullable = false, length = 500)
    private String popis;

    /**
     * Vzťah many-to-one k poistnej zmluve.
     * Každá poistná udalosť musí byť priradená k existujúcej poistnej zmluve.
     * Väzba je realizovaná cez stĺpec "zmluva_id" v tabuľke "udalosti".
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "zmluva_id", nullable = false)
    private PoistnaZmluva poistnaZmluva;

    /**
     * Základné gettery a settery pre atribúty entity
     */

    /**
     * Získa ID poistnej udalosti.
     *
     * @return identifikátor poistnej udalosti
     */
    public Long getId() {
        return id;
    }

    /**
     * Nastaví ID poistnej udalosti.
     * Tento setter sa zvyčajne používa len pri JPA operáciách.
     *
     * @param id identifikátor poistnej udalosti
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Získa dátum poistnej udalosti.
     *
     * @return dátum, kedy nastala poistná udalosť
     */
    public LocalDate getDatum() {
        return datum;
    }

    /**
     * Nastaví dátum poistnej udalosti.
     *
     * @param datum dátum, kedy nastala poistná udalosť
     */
    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    /**
     * Získa popis poistnej udalosti.
     *
     * @return textový popis poistnej udalosti
     */
    public String getPopis() {
        return popis;
    }

    /**
     * Nastaví popis poistnej udalosti.
     *
     * @param popis textový popis poistnej udalosti
     */
    public void setPopis(String popis) {
        this.popis = popis;
    }

    /**
     * Získa poistnú zmluvu, ku ktorej sa vzťahuje táto poistná udalosť.
     *
     * @return entita poistnej zmluvy
     */
    public PoistnaZmluva getPoistnaZmluva() {
        return poistnaZmluva;
    }

    /**
     * Nastaví poistnú zmluvu, ku ktorej sa vzťahuje táto poistná udalosť.
     *
     * @param poistnaZmluva entita poistnej zmluvy
     */
    public void setPoistnaZmluva(PoistnaZmluva poistnaZmluva) {
        this.poistnaZmluva = poistnaZmluva;
    }
}