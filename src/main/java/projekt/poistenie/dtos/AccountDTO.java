package projekt.poistenie.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * AccountDTO slúži na prenos prihlasovacích a registračných údajov
 * medzi front-endom (HTML formulár / JSON) a back-endom bez obsahovania
 * biznis logiky alebo perzistencie.
 */
public class AccountDTO {

    /** Email používateľa – nemôže byť prázdny a musí byť platný e-mail. */
    @NotBlank(message = "Email je povinný")
    @Email(message = "Zadajte platný email")
    private String email;

    /** Heslo – nemôže byť prázdne. */
    @NotBlank(message = "Heslo je povinné")
    private String password;

    /** Potvrdenie hesla – musí byť zadané, kontrolujeme zhodu s password. */
    @NotBlank(message = "Potvrdenie hesla je povinné")
    private String passwordConfirmation;

    // ---- Gettery a settery ----

    /**
     * @return email zadaný vo formulári
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email nastaví email, ktorý prišiel z formulára
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return heslo zadané vo formulári (plaintext, ešte nezašifrované)
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password nastaví heslo z formulára
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return potvrdenie hesla zadané vo formulári
     */
    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    /**
     * @param passwordConfirmation nastaví potvrdenie hesla
     */
    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
