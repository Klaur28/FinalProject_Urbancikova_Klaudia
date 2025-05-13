package projekt.poistenie.dtos.mappers;

import org.springframework.stereotype.Component;
import projekt.poistenie.dtos.StatistikyDTO;
import projekt.poistenie.entities.Statistiky;

/**
 * Mapper pre konverziu medzi entitou Statistiky a jej DTO objektom.
 * Na rozdiel od PoistnaZmluvaMapper, táto trieda nepoužíva MapStruct,
 * ale implementuje mapovanie manuálne.
 */
@Component
public class StatistikyMapper {

    /**
     * Konvertuje DTO objekt na entitu.
     * Vytvára novú inštanciu Statistiky a nastavuje jej hodnoty z DTO objektu.
     *
     * @param dto zdrojový DTO objekt s údajmi štatistík
     * @return novovytvorená entita Statistiky s dátami z DTO
     */
    public Statistiky toEntity(StatistikyDTO dto) {
        Statistiky statistiky = new Statistiky();
        // Nastavenie hodnôt zo zdrojového DTO
        statistiky.setPocetPoistenych(dto.getPocetPoistenych());
        statistiky.setPocetPoisteni(dto.getPocetPoisteni());
        statistiky.setPocetUdalosti(dto.getPocetUdalosti());
        return statistiky;
    }

    /**
     * Konvertuje entitu na DTO objekt.
     * Vytvára novú inštanciu StatistikyDTO pomocou údajov z entity.
     *
     * @param entity zdrojová entita štatistík
     * @return novovytvorený DTO objekt s dátami z entity
     */
    public StatistikyDTO toDto(Statistiky entity) {
        // Použitie konštruktora pre vytvorenie nového DTO objektu
        return new StatistikyDTO(
                entity.getPocetPoistenych(),
                entity.getPocetPoisteni(),
                entity.getPocetUdalosti()
        );
    }
}