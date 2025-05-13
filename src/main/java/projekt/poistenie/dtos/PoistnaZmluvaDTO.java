package projekt.poistenie.dtos;

import jakarta.validation.constraints.NotNull;
import projekt.poistenie.entities.RelationType;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) pre poistnú zmluvu.
 * Používa sa na prenos údajov o poistnej zmluve medzi rôznymi vrstvami aplikácie
 * (prezentačná vrstva, servisná vrstva, databázová vrstva).
 *
 * Obsahuje základné údaje o zmluve (číslo, typ, platnosť) a tiež odkazové údaje
 * o poistencovi (ID, meno, priezvisko).
 */
public class PoistnaZmluvaDTO {

    /**
     * Jedinečný identifikátor poistnej zmluvy.
     */
    private Long id;

    /**
     * Číslo poistnej zmluvy - zvyčajne vo formáte určenom poisťovňou.
     */
    private String cisloZmluvy;

    /**
     * Druh poistenia, napríklad životné, majetkové, cestovné, zodpovednosť.
     */
    private String druhPoistenia;

    /**
     * Dátum začiatku platnosti poistenia.
     */
    private LocalDate platnostOd;

    /**
     * Dátum konca platnosti poistenia.
     */
    private LocalDate platnostDo;

    /**
     * Poistná suma v eurách.
     */
    private Double suma;

    /**
     * Typ vzťahu poistenca k zmluve.
     * Nemôže byť null - je označené anotáciou @NotNull pre validáciu.
     */
    @NotNull
    private RelationType relation;

    /**
     * ID poistenca, ktorý je viazaný na túto poistnú zmluvu.
     */
    private Long poistenecId;

    /**
     * Meno poistenca - uchováva sa pre zobrazenie v UI bez nutnosti
     * dodatočného dopytu na údaje poistenca.
     */
    private String poistenecMeno;

    /**
     * Priezvisko poistenca - uchováva sa pre zobrazenie v UI bez nutnosti
     * dodatočného dopytu na údaje poistenca.
     */
    private String poistenecPriezvisko;

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
     *
     * @param id identifikátor poistnej zmluvy
     */
    public void setId(Long id) {
        this.id = id;
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

    /**
     * Získa ID poistenca.
     *
     * @return identifikátor poistenca
     */
    public Long getPoistenecId() {
        return poistenecId;
    }

    /**
     * Nastaví ID poistenca.
     *
     * @param poistenecId identifikátor poistenca
     */
    public void setPoistenecId(Long poistenecId) {
        this.poistenecId = poistenecId;
    }

    /**
     * Získa meno poistenca.
     *
     * @return meno poistenca
     */
    public String getPoistenecMeno() {
        return poistenecMeno;
    }

    /**
     * Nastaví meno poistenca.
     *
     * @param poistenecMeno meno poistenca
     */
    public void setPoistenecMeno(String poistenecMeno) {
        this.poistenecMeno = poistenecMeno;
    }

    /**
     * Získa priezvisko poistenca.
     *
     * @return priezvisko poistenca
     */
    public String getPoistenecPriezvisko() {
        return poistenecPriezvisko;
    }

    /**
     * Nastaví priezvisko poistenca.
     *
     * @param poistenecPriezvisko priezvisko poistenca
     */
    public void setPoistenecPriezvisko(String poistenecPriezvisko) {
        this.poistenecPriezvisko = poistenecPriezvisko;
    }
}