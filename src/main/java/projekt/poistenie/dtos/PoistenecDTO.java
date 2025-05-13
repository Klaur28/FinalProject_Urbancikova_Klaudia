package projekt.poistenie.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * PoistenecDTO slúži na prenos dát poistenca medzi webovými formulármi
 * a backendom bez perzistencie či biznis logiky.
 */
public class PoistenecDTO {

    /** Unikátne ID poistenca. */
    private Long id;

    /** Meno poistenca – povinné, max. 50 znakov. */
    @NotBlank(message = "Meno je povinné")
    @Size(max = 50, message = "Meno môže mať maximálne 50 znakov")
    private String meno;

    /** Priezvisko poistenca – povinné, max. 50 znakov. */
    @NotBlank(message = "Priezvisko je povinné")
    @Size(max = 50, message = "Priezvisko môže mať maximálne 50 znakov")
    private String priezvisko;

    /** E-mail poistenca – povinný, musí byť validný e-mail. */
    @NotBlank(message = "Email je povinný")
    @Email(message = "Zadajte platný email")
    private String email;

    /** Telefónne číslo – povinné, platný formát +[0-9 ]{9,15}. */
    @NotBlank(message = "Telefón je povinný")
    @Pattern(regexp = "\\+?[0-9 ]{9,15}", message = "Zadajte platné telefónne číslo")
    private String telefon;

    /** Ulica – povinná hodnota. */
    @NotBlank(message = "Ulica je povinná")
    private String ulica;

    /** Mesto – povinné. */
    @NotBlank(message = "Mesto je povinné")
    private String mesto;

    /** PSČ – povinné. */
    @NotBlank(message = "PSČ je povinné")
    private String psc;

    // ---------------------- Gettery a Settery ----------------------

    /**
     * @return ID poistenca
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id nastaví ID poistenca
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return meno poistenca z formulára
     */
    public String getMeno() {
        return meno;
    }

    /**
     * @param meno nastaví meno poistenca
     */
    public void setMeno(String meno) {
        this.meno = meno;
    }

    /**
     * @return priezvisko poistenca z formulára
     */
    public String getPriezvisko() {
        return priezvisko;
    }

    /**
     * @param priezvisko nastaví priezvisko poistenca
     */
    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }

    /**
     * @return e-mail poistenca z formulára
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email nastaví e-mail poistenca
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return telefónne číslo poistenca z formulára
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * @param telefon nastaví telefónne číslo poistenca
     */
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    /**
     * @return ulica poistenca z formulára
     */
    public String getUlica() {
        return ulica;
    }

    /**
     * @param ulica nastaví ulicu poistenca
     */
    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    /**
     * @return mesto poistenca z formulára
     */
    public String getMesto() {
        return mesto;
    }

    /**
     * @param mesto nastaví mesto poistenca
     */
    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    /**
     * @return PSČ poistenca z formulára
     */
    public String getPsc() {
        return psc;
    }

    /**
     * @param psc nastaví PSČ poistenca
     */
    public void setPsc(String psc) {
        this.psc = psc;
    }
}
