package projekt.poistenie.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import projekt.poistenie.dtos.PoistnaZmluvaDTO;
import projekt.poistenie.entities.PoistnaZmluva;

/**
 * Rozhranie pre mapovanie medzi entitou PoistnaZmluva a jej DTO objektom.
 * Využíva knižnicu MapStruct pre automatické generovanie implementácie.
 *
 * Trieda je označená ako Mapper so Spring komponentovým modelom, čo znamená,
 * že implementácia rozhrania bude automaticky registrovaná ako Spring bean.
 */
@Mapper(componentModel = "spring")
public interface PoistnaZmluvaMapper {

    /**
     * Konvertuje DTO objekt na entitu.
     *
     * @param source zdrojový DTO objekt s údajmi poistnej zmluvy
     * @return novovytvorená entita PoistnaZmluva s dátami z DTO
     */
    PoistnaZmluva toEntity(PoistnaZmluvaDTO source);

    /**
     * Konvertuje entitu na DTO objekt.
     *
     * @param source zdrojová entita poistnej zmluvy
     * @return novovytvorený DTO objekt s dátami z entity
     */
    PoistnaZmluvaDTO toDto(PoistnaZmluva source);

    /**
     * Aktualizuje existujúcu entitu PoistnaZmluva údajmi z DTO objektu.
     * Parameter @MappingTarget označuje cieľový objekt, ktorý sa má aktualizovať.
     *
     * @param source zdrojový DTO objekt s novými údajmi
     * @param target cieľová entita, ktorá sa má aktualizovať
     */
    void updatePoistnaZmluva(PoistnaZmluvaDTO source, @MappingTarget PoistnaZmluva target);
}