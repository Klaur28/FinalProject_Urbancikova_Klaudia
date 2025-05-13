package projekt.poistenie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projekt.poistenie.entities.Poistenec;

import java.util.Optional;

/**
 * Repository pre prácu s entitami Poistenec v databáze.
 * Rozširuje JpaRepository, čím získa metódy pre základné CRUD operácie:
 * - save(), findById(), findAll(), deleteById() a ďalšie.
 */
public interface PoistenecRepository extends JpaRepository<Poistenec, Long> {

    /**
     * Vyhľadá poistenca podľa jeho e-mailu.
     *
     * @param email e-mail poistenca
     * @return Optional obsahujúci Poistenec, ak existuje, inak prázdny
     */
    Optional<Poistenec> findByEmail(String email);

    /**
     * Overí, či poistencovi s daným ID patrí práve zadaný e-mail.
     * Používa sa napr. na kontrolu vlastníctva záznamu.
     *
     * @param id    ID poistenca
     * @param email e-mail na overenie
     * @return true, ak poistencovo ID aj e-mail zodpovedajú záznamu v DB
     */
    boolean existsByIdAndEmail(Long id, String email);

}
