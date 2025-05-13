package projekt.poistenie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projekt.poistenie.dtos.AccountDTO;
import projekt.poistenie.entities.Account;
import projekt.poistenie.exceptions.DuplicateEmailException;
import projekt.poistenie.exceptions.PasswordsDoNotEqualException;
import projekt.poistenie.repository.AccountRepository;

/**
 * Služba pre správu autentifikácie a registrácie používateľov.
 * Poskytuje metódy pre registráciu nových používateľov a súvisiace operácie.
 */
@Service
public class AuthService {

    /**
     * Repository pre prístup k dátam používateľských účtov.
     */
    private final AccountRepository accountRepository;

    /**
     * Enkodér hesiel pre bezpečné ukladanie hesiel.
     * Spring Security odporúča použiť BCryptPasswordEncoder.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Konštruktor s dependency injection.
     *
     * @param accountRepository repository pre prístup k dátam používateľských účtov
     * @param passwordEncoder enkodér hesiel
     */
    @Autowired
    public AuthService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Zaregistruje nového používateľa do systému.
     * Metóda validuje, či sa heslá zhodujú a či email nie je už v systéme použitý.
     * Heslo je pred uložením zahashované pomocou PasswordEncoder.
     *
     * @param accountDTO objekt obsahujúci údaje o novom používateľovi vrátane hesla a jeho potvrdenia
     * @throws PasswordsDoNotEqualException ak sa heslo a jeho potvrdenie nezhodujú
     * @throws DuplicateEmailException ak už v systéme existuje používateľ s rovnakým emailom
     */
    public void registerNewUser(AccountDTO accountDTO) throws DuplicateEmailException, PasswordsDoNotEqualException {
        // Kontrola, či sa heslo a jeho potvrdenie zhodujú
        if (!accountDTO.getPassword().equals(accountDTO.getPasswordConfirmation())) {
            throw new PasswordsDoNotEqualException();
        }

        // Kontrola, či email nie je už použitý
        if (accountRepository.findByEmail(accountDTO.getEmail()).isPresent()) {
            throw new DuplicateEmailException();
        }

        // Vytvorenie a uloženie nového účtu
        Account account = new Account();
        account.setEmail(accountDTO.getEmail());
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword())); // Zahašovanie hesla
        accountRepository.save(account);
    }
}