package projekt.poistenie.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Entita reprezentujúca poistnú zmluvu v systéme.
 * Predstavuje perzistentný objekt ukladaný do databázy, ktorý obsahuje všetky
 * informácie o poistnej zmluve vrátane vzťahu k poistencovi a poistným udalostiam.
 *
 * Je mapovaná na tabuľku "poistne_zmluvy" v databáze.
 */
@Entity
@Table(name = "poistne_zmluvy")
public class PoistnaZmluva {

    /**
     * Jedinečný identifikátor poistnej zmluvy.
     * Hodnota je generovaná automaticky databázou pri vložení nového záznamu.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Vzťah many-to-one k poistencovi.
     * Každá poistná zmluva musí byť priradená k existujúcemu poistencovi.
     * Použitie FetchType.LAZY znamená, že údaje o poistencovi sa načítajú z databázy
     * až pri priamom prístupe k tomuto atribútu.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "poistenec_id", nullable = false)
    private Poistenec poistenec;

    /**
     * Zoznam poistných udalostí súvisiacich s touto poistnou zmluvou.
     * Vzťah one-to-many - jedna poistná zmluva môže mať viacero poistných udalostí.
     * CascadeType.ALL znamená, že operácie (uloženie, aktualizácia, vymazanie) sa
     * prenesú aj na súvisiace poistné udalosti.
     * orphanRemoval=true znamená, že keď sa odstráni poistná udalosť zo zoznamu, bude
     * automaticky vymazaná z databázy.
     */
    @OneToMany(mappedBy = "poistnaZmluva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Udalost> udalosti;

    /**
     * Jedinečné číslo poistnej zmluvy.
     * Číslo musí byť unikátne v celej databáze a nesmie byť null.
     */
    @Column(name = "cislo_zmluvy", nullable = false, unique = true)
    private String cisloZmluvy;

    /**
     * Druh/typ poistenia (napr. životné, majetkové, cestovné, zodpovednosť).
     * Hodnota nesmie byť null.
     */
    @Column(name = "druh_poistenia", nullable = false)
    private String druhPoistenia;

    /**
     * Dátum začiatku platnosti poistnej zmluvy.
     * Hodnota nesmie byť null.
     */
    @Column(name = "platnost_od", nullable = false)
    private LocalDate platnostOd;

    /**
     * Dátum konca platnosti poistnej zmluvy.
     * Hodnota nesmie byť null.
     */
    @Column(name = "platnost_do", nullable = false)
    private LocalDate platnostDo;

    /**
     * Poistná suma v eurách.
     * Hodnota nesmie byť null.
     */
    @Column(nullable = false)
    private Double suma;

    /**
     * Typ vzťahu poistenca k poistnej zmluve (platiteľ, poistený).
     * Je uložený ako reťazec (EnumType.STRING) a nesmie byť null.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RelationType relation;

    // Gettery a Settery

    /**
     * Získa ID poistnej zmluvy.
     *
     * @return identifikátor poistnej zmluvy
     */
    public Long getId() {
        return id;
    }

    /**
     * Nastaví ID poistnej zmluvy.
     * Tento setter sa zvyčajne používa len pri JPA operáciách.
     *
     * @param id identifikátor poistnej zmluvy
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Získa poistenca, ktorý je viazaný na túto poistnú zmluvu.
     *
     * @return entita poistenca
     */
    public Poistenec getPoistenec() {
        return poistenec;
    }

    /**
     * Nastaví poistenca pre túto poistnú zmluvu.
     *
     * @param poistenec entita poistenca
     */
    public void setPoistenec(Poistenec poistenec) {
        this.poistenec = poistenec;
    }

    /**
     * Získa zoznam poistných udalostí súvisiacich s touto zmluvou.
     *
     * @return zoznam poistných udalostí
     */
    public List<Udalost> getUdalosti() {
        return udalosti;
    }

    /**
     * Nastaví zoznam poistných udalostí pre túto zmluvu.
     *
     * @param udalosti zoznam poistných udalostí
     */
    public void setUdalosti(List<Udalost> udalosti) {
        this.udalosti = udalosti;
    }

    /**
     * Získa číslo poistnej zmluvy.
     *
     * @return číslo poistnej zmluvy
     */
    public String getCisloZmluvy() {
        return cisloZmluvy;
    }

    /**
     * Nastaví číslo poistnej zmluvy.
     *
     * @param cisloZmluvy číslo poistnej zmluvy
     */
    public void setCisloZmluvy(String cisloZmluvy) {
        this.cisloZmluvy = cisloZmluvy;
    }

    /**
     * Získa druh poistenia.
     *
     * @return druh poistenia
     */
    public String getDruhPoistenia() {
        return druhPoistenia;
    }

    /**
     * Nastaví druh poistenia.
     *
     * @param druhPoistenia druh poistenia
     */
    public void setDruhPoistenia(String druhPoistenia) {
        this.druhPoistenia = druhPoistenia;
    }

    /**
     * Získa dátum začiatku platnosti poistenia.
     *
     * @return dátum začiatku platnosti
     */
    public LocalDate getPlatnostOd() {
        return platnostOd;
    }

    /**
     * Nastaví dátum začiatku platnosti poistenia.
     *
     * @param platnostOd dátum začiatku platnosti
     */
    public void setPlatnostOd(LocalDate platnostOd) {
        this.platnostOd = platnostOd;
    }

    /**
     * Získa dátum konca platnosti poistenia.
     *
     * @return dátum konca platnosti
     */
    public LocalDate getPlatnostDo() {
        return platnostDo;
    }

    /**
     * Nastaví dátum konca platnosti poistenia.
     *
     * @param platnostDo dátum konca platnosti
     */
    public void setPlatnostDo(LocalDate platnostDo) {
        this.platnostDo = platnostDo;
    }

    /**
     * Získa poistnú sumu.
     *
     * @return poistná suma v eurách
     */
    public Double getSuma() {
        return suma;
    }

    /**
     * Nastaví poistnú sumu.
     *
     * @param suma poistná suma v eurách
     */
    public void setSuma(Double suma) {
        this.suma = suma;
    }

    /**
     * Získa typ vzťahu poistenca k zmluve.
     *
     * @return typ vzťahu ako enum hodnota RelationType
     */
    public RelationType getRelation() {
        return relation;
    }

    /**
     * Nastaví typ vzťahu poistenca k zmluve.
     *
     * @param relation typ vzťahu ako enum hodnota RelationType
     */
    public void setRelation(RelationType relation) {
        this.relation = relation;
    }
}