package projekt.poistenie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projekt.poistenie.dtos.AccountDTO;
import projekt.poistenie.entities.Account;
import projekt.poistenie.exceptions.DuplicateEmailException;
import projekt.poistenie.exceptions.PasswordsDoNotEqualException;
import projekt.poistenie.repository.AccountRepository;

import java.util.Optional;

/**
 * Implementácia AccountService pre správu používateľských účtov.
 * Zabezpečuje registráciu, overovanie existujúcich e-mailov a integráciu so Spring Security.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Vytvorí nový účet (USER alebo ADMIN podľa parametra isAdmin).
     * Metóda je v transakcii, aby sa pri chybe rollbackovali zmeny.
     *
     * @param accountDTO DTO s údajmi z formulára (email, password, passwordConfirmation)
     * @param isAdmin    true = ADMIN rola, false = USER rola
     * @return práve uložený Account objekt (vrátený z repository)
     * @throws PasswordsDoNotEqualException ak sa heslá nezhodujú
     * @throws DuplicateEmailException      ak už e-mail existuje alebo nastane porušenie unikátnosti
     */
    @Override
    @Transactional
    public Account create(AccountDTO accountDTO, boolean isAdmin) {
        // 1) Validácia vstupných dát
        validateAccountData(accountDTO);

        // 2) Vytvorenie entity a hashovanie hesla
        Account account = new Account();
        account.setEmail(accountDTO.getEmail());
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        account.setRole(isAdmin ? Account.Role.ADMIN : Account.Role.USER);

        try {
            // 3) Uloženie do DB
            return accountRepository.save(account);
        } catch (DataIntegrityViolationException e) {
            // Pri porušení unikátnosti e-mailu
            throw new DuplicateEmailException();
        }
    }

    /**
     * Validuje vstupné dáta pre nový účet.
     *
     * @param accountDTO DTO na validáciu
     * @throws PasswordsDoNotEqualException ak sa heslá nezhodujú
     * @throws DuplicateEmailException ak e-mail už existuje
     */
    private void validateAccountData(AccountDTO accountDTO) {
        // Overenie zhody hesla a potvrdenia
        if (!accountDTO.getPassword().equals(accountDTO.getPasswordConfirmation())) {
            throw new PasswordsDoNotEqualException();
        }

        // Overenie, či e-mail už neexistuje
        if (accountRepository.existsByEmail(accountDTO.getEmail())) {
            throw new DuplicateEmailException();
        }
    }

    /**
     * Nájde účet podľa e-mailu, ak existuje.
     *
     * @param email e-mail používateľa
     * @return Optional obsahujúci Account, ak nájdený
     */
    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    /**
     * Metóda z UserDetailsService pre autentifikáciu používateľa.
     * Spring Security ju volá pri prihlasovaní.
     *
     * @param username e-mail (slúži ako username)
     * @return UserDetails objekt (Account implementuje UserDetails)
     * @throws UsernameNotFoundException ak e-mail nie je v DB
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Používateľ s emailom " + username + " nebol nájdený")
                );
    }

    /**
     * Verejná registračná metóda pre nový účet s rolou USER.
     * Volá create() s isAdmin = false.
     *
     * @param dto DTO so vstupnými údajmi
     * @return práve vytvorený Account
     */
    @Override
    @Transactional
    public Account register(AccountDTO dto) {
        return create(dto, false);
    }

    /**
     * Verejná registračná metóda pre nový admin účet s rolou ADMIN.
     *
     * @param dto DTO so vstupnými údajmi
     * @return práve vytvorený Account
     */
    @Override
    @Transactional
    public Account registerAdmin(AccountDTO dto) {
        return create(dto, true);
    }
}