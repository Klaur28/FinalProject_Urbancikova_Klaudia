package projekt.poistenie.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import projekt.poistenie.dtos.PoistenecDTO;
import projekt.poistenie.entities.Poistenec;

/**
 * MapStruct mapper pre konverziu medzi PoistenecDTO a Poistenec entitou.
 * Vygenerovaná implementácia bude registrovaná ako Spring bean.
 */
@Mapper(componentModel = "spring")
public interface PoistenecMapper {

    /**
     * Premení PoistenecDTO na Poistenec entitu.
     * Používa sa pri vytváraní alebo ukladaní nového poistenca do DB.
     *
     * @param source DTO obsahujúce dáta z formulára
     * @return nová Poistenec entita pripravená na ukladanie
     */
    Poistenec toEntity(PoistenecDTO source);

    /**
     * Premení Poistenec entitu na PoistenecDTO.
     * Používa sa pri načítaní údajov z DB a odovzdávaní na front-end.
     *
     * @param source entita načítaná z repozitára
     * @return DTO pripravené pre šablónu alebo JSON odpoveď
     */
    PoistenecDTO toDto(Poistenec source);

    /**
     * Aktualizuje existujúcu Poistenec entitu dátami z DTO.
     * Používa sa pri úprave existujúceho záznamu (update).
     *
     * @param source  DTO s novými hodnotami
     * @param target  existujúca entita, ktorú je potrebné upraviť
     */
    void updatePoistenec(PoistenecDTO source, @MappingTarget Poistenec target);
}
