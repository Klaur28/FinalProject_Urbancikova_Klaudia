package projekt.poistenie.service;

import projekt.poistenie.dtos.PoistenecDTO;

import java.util.List;

/**
 * Rozhranie PoistenecService definuje biznis operácie nad poistencami.
 * Implementácia tejto služby sa stará o validáciu, mapovanie DTO ↔ Entity
 * a komunikáciu s repository vrstvou.
 */
public interface PoistenecService {

    /**
     * Vytvorí nového poistenca na základe údajov z DTO.
     *
     * @param poistenec DTO obsahujúce údaje poistenca (meno, email, atď.)
     */
    void create(PoistenecDTO poistenec);

    /**
     * Načíta a vráti všetkých poistencov ako zoznam DTO.
     *
     * @return zoznam PoistenecDTO pre všetkých poistencov v DB
     */
    List<PoistenecDTO> findAll();

    /**
     * Nájde a vráti poistenca podľa jeho ID.
     * Ak sa poistenca s daným ID nenájde, implementácia vyhodí
     * PoistenecNotFoundException s HTTP stavom 404.
     *
     * @param id jedinečný identifikátor poistenca
     * @return PoistenecDTO so všetkými potrebnými údajmi
     */
    PoistenecDTO findById(long id);

    /**
     * Upraví existujúceho poistenca údajmi z DTO.
     * DTO musí obsahovať platné ID existujúceho poistenca.
     *
     * @param poistenec DTO so zmenenými poľami a ID poistenca
     */
    void update(PoistenecDTO poistenec);

    /**
     * Odstráni poistenca podľa jeho ID.
     *
     * @param id ID poistenca, ktorého chceme vymazať
     */
    void delete(long id);

    /**
     * Nájde a vráti poistenca podľa jeho e-mailu.
     * Používa sa pre overenie vlastníctva a zobrazenie vlastného profilu.
     * Ak sa poistenec nenájde, vyhodí PoistenecNotFoundException.
     *
     * @param email e-mail poistenca
     * @return PoistenecDTO daného poistenca
     */
    PoistenecDTO findByEmail(String email);

    /**
     * Skontroluje, či poistenca so zadaným ID vlastní používateľ
     * s daným e-mailom. Používa sa pri autorizácii prístupu k záznamu.
     *
     * @param zmluvaId ID poistenca (alebo súvisiaceho záznamu)
     * @param email    e-mail prihlaseného používateľa
     * @return true, ak e-mail zodpovedá vlastníctvu poistenca s týmto ID
     */
    boolean isOwnedByEmail(Long zmluvaId, String email);
}
