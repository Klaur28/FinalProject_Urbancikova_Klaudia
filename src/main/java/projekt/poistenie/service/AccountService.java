package projekt.poistenie.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import projekt.poistenie.dtos.AccountDTO;
import projekt.poistenie.entities.Account;

import java.util.Optional;

/**
 * AccountService spravuje používateľské účty v aplikácii.
 * Rozširuje UserDetailsService, aby poskytovalo integráciu so Spring Security.
 */
public interface AccountService extends UserDetailsService {

    /**
     * Vytvorí nový používateľský účet z údajov z DTO.
     *
     * @param accountDTO obsahuje e-mail a heslo z registračného formulára
     * @param isAdmin    či má byť účet vytvorený s rolou ADMIN (true) alebo USER (false)
     * @return práve uložený Account entitu s vygenerovaným ID a nastavenou rolou
     */
    Account create(AccountDTO accountDTO, boolean isAdmin);

    /**
     * Nájde účet podľa e-mailu.
     *
     * @param email e-mail používateľa
     * @return Optional obsahujúci Account, ak existuje, inak prázdny
     */
    Optional<Account> findByEmail(String email);

    /**
     * Zaregistruje nového používateľa s rolou USER.
     *
     * @param accountDTO dáta pre nový účet
     * @return vytvorený účet
     */
    Account register(AccountDTO accountDTO);

    /**
     * Zaregistruje nového používateľa s rolou ADMIN.
     *
     * @param accountDTO dáta pre nový admin účet
     * @return vytvorený admin účet
     */
    Account registerAdmin(AccountDTO accountDTO);

    // Metóda loadUserByUsername() z UserDetailsService je dedičná a používa sa na autentifikáciu.
}