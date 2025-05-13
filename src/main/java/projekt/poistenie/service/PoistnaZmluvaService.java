package projekt.poistenie.service;

import org.springframework.transaction.annotation.Transactional;
import projekt.poistenie.dtos.PoistnaZmluvaDTO;
import java.util.List;

/**
 * Služba pre správu poistných zmlúv.
 * Poskytuje operácie pre vytváranie, zobrazovanie, upravovanie a mazanie poistných zmlúv.
 */
public interface PoistnaZmluvaService {

    /**
     * Vytvorí novú poistnú zmluvu.
     *
     * @param dto objekt s údajmi o novej poistnej zmluve
     */
    void create(PoistnaZmluvaDTO dto);

    /**
     * Vráti zoznam všetkých poistných zmlúv.
     *
     * @return zoznam všetkých poistných zmlúv v systéme
     */
    List<PoistnaZmluvaDTO> findAll();

    /**
     * Vyhľadá poistnú zmluvu podľa ID.
     *
     * @param id identifikátor poistnej zmluvy
     * @return nájdená poistná zmluva alebo vyhodí výnimku, ak zmluva neexistuje
     */
    PoistnaZmluvaDTO findById(Long id);

    /**
     * Aktualizuje existujúcu poistnú zmluvu.
     *
     * @param dto objekt s aktualizovanými údajmi poistnej zmluvy
     */
    void update(PoistnaZmluvaDTO dto);

    /**
     * Odstráni poistnú zmluvu podľa ID.
     *
     * @param id identifikátor poistnej zmluvy na odstránenie
     */
    void delete(Long id);

    List<PoistnaZmluvaDTO> getAll();

    PoistnaZmluvaDTO getById(long id);

    @Transactional
    void edit(PoistnaZmluvaDTO poistnaZmluvaDTO);

    /**
     * Vyhľadá všetky poistné zmluvy, ktoré patria poistencovi s daným emailom.
     *
     * @param email email poistenca
     * @return zoznam poistných zmlúv patriacich danému poistencovi
     */
    List<PoistnaZmluvaDTO> findByPoistenecEmail(String email);

    /**
     * Overí, či poistná zmluva s daným ID patrí poistencovi s daným emailom.
     * Používa sa na kontrolu oprávnení - či má prihlásený používateľ prístup k danej zmluve.
     *
     * @param zmluvaId identifikátor poistnej zmluvy
     * @param email email poistenca
     * @return true ak zmluva patrí danému poistencovi, inak false
     */
    boolean belongsToEmail(Long zmluvaId, String email);
}