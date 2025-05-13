package projekt.poistenie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projekt.poistenie.entities.Account;

import java.util.Optional;

/**
 * AccountRepository je Spring Data JPA rozhranie pre prístup k entite Account.
 * Automagicky poskytuje základné CRUD operácie (save, findById, findAll, deleteById).
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Nájde účet podľa jedinečného e-mailu.
     *
     * @param email e-mail používateľa
     * @return Optional obsahujúci nájdený Account alebo prázdny, ak neexistuje
     */
    Optional<Account> findByEmail(String email);

    /**
     * Skontroluje, či už účet s daným e-mailom existuje.
     *
     * @param email e-mail na overenie
     * @return true, ak účet existuje, inak false
     */
    boolean existsByEmail(String email);
}
