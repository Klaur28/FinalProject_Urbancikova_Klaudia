package projekt.poistenie.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entita Poistenec reprezentuje klienta v systéme.
 * Mapuje sa na tabuľku "poistenec" v databáze.
 */
@Entity
@Table(name = "poistenec")
public class Poistenec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Meno poistenca. */
    private String meno;

    /** Priezvisko poistenca. */
    private String priezvisko;

    /** E-mail poistenca. */
    private String email;

    /** Telefónne číslo poistenca. */
    private String telefon;

    /** Ulica bydliska poistenca. */
    private String ulica;

    /** Mesto bydliska poistenca. */
    private String mesto;

    /** PSČ bydliska poistenca. */
    private String psc;

    /**
     * Vzťah 1:N na entitu PoistnaZmluva.
     * - mappedBy = "poistenec": zmluvy referencujú späť na túto entitu.
     * - cascade = ALL: operácie nad poistencami sa prenesú na zmluvy (uloženie, zmazanie...).
     * - orphanRemoval = true: zmluvy bez nadradenej entity sa automaticky vymažú.
     */
    @OneToMany(mappedBy = "poistenec", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PoistnaZmluva> zmluvy = new ArrayList<>();

    // ---------------------- Gettery a Settery ----------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getPriezvisko() {
        return priezvisko;
    }

    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getPsc() {
        return psc;
    }

    public void setPsc(String psc) {
        this.psc = psc;
    }

    /**
     * @return zoznam poistných zmlúv pre tohto poistenca.
     */
    public List<PoistnaZmluva> getZmluvy() {
        return zmluvy;
    }

    /**
     * @param zmluvy nastaví nový zoznam zmlúv (používa sa pri načítaní/aktualizácii).
     */
    public void setZmluvy(List<PoistnaZmluva> zmluvy) {
        this.zmluvy = zmluvy;
    }
}
