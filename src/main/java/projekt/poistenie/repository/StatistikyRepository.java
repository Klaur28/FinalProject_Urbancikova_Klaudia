package projekt.poistenie.repository;

import org.springframework.data.repository.CrudRepository;
import projekt.poistenie.entities.Statistiky;

/**
 * Repository rozhranie pre prácu s entitou Statistiky.
 * Rozširuje CrudRepository, čím získava základné CRUD operácie (create, read, update, delete).
 *
 * Toto rozhranie neobsahuje žiadne dodatočné metódy, pretože pre štatistiky
 * postačujú štandardné operácie poskytované CrudRepository.
 *
 * Entity Statistiky typicky reprezentujú agregované hodnoty, ktoré sú generované
 * na základe dát v iných tabuľkách a následne uložené pre rýchly prístup.
 */
public interface StatistikyRepository extends CrudRepository<Statistiky, Long> {
}