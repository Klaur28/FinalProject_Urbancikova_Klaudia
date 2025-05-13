package projekt.poistenie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projekt.poistenie.entities.Poistenec;
import projekt.poistenie.entities.PoistnaZmluva;

import java.util.List;

/**
 * Repository rozhranie pre prácu s entitou PoistnaZmluva.
 * Rozširuje JpaRepository, čím získava základné CRUD operácie (create, read, update, delete)
 * a ďalšie funkcie pre prístup k údajom.
 */
@Repository
public interface PoistnaZmluvaRepository extends JpaRepository<PoistnaZmluva, Long> {

    /**
     * Vyhľadá všetky poistné zmluvy patriace konkrétnemu poistencovi.
     *
     * @param poistenec poistenec, ktorého zmluvy sa majú vyhľadať
     * @return zoznam poistných zmlúv prislúchajúcich danému poistencovi
     */
    List<PoistnaZmluva> findByPoistenec(Poistenec poistenec);

    /**
     * Overí, či existuje poistná zmluva s daným ID a zároveň patrí poistencovi
     * s daným emailom.
     * Táto metóda sa používa na kontrolu oprávnení - či má prihlásený používateľ
     * prístup ku konkrétnej zmluve.
     *
     * @param id identifikátor poistnej zmluvy
     * @param email email poistenca
     * @return true ak existuje zmluva s daným ID patriaca poistencovi s daným emailom, inak false
     */
    boolean existsByIdAndPoistenecEmail(Long id, String email);

}