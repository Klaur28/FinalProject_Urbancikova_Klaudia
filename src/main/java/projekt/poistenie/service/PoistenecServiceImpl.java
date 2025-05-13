package projekt.poistenie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projekt.poistenie.dtos.PoistenecDTO;
import projekt.poistenie.entities.Poistenec;
import projekt.poistenie.exceptions.PoistenecNotFoundException;
import projekt.poistenie.dtos.mappers.PoistenecMapper;
import projekt.poistenie.repository.PoistenecRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementácia PoistenecService, ktorá spracováva biznis logiku
 * pre CRUD operácie s poistencami.
 */
@Service
public class PoistenecServiceImpl implements PoistenecService {

    private final PoistenecRepository poistenecRepository;
    private final PoistenecMapper poistenecMapper;

    /**
     * Konštruktor pre injection závislostí.
     *
     * @param poistenecRepository repozitár pre operácie s entitou Poistenec
     * @param poistenecMapper mapper pre konverziu medzi DTO a entitou
     */
    @Autowired
    public PoistenecServiceImpl(PoistenecRepository poistenecRepository, PoistenecMapper poistenecMapper) {
        this.poistenecRepository = poistenecRepository;
        this.poistenecMapper = poistenecMapper;
    }

    /**
     * Vytvorí nového poistenca.
     * Metóda beží v transakcii, aby sa v prípade chyby rollbackovali zmeny.
     *
     * @param poistenecDTO DTO s údajmi z formulára
     */
    @Override
    @Transactional
    public void create(PoistenecDTO poistenecDTO) {
        // Validácia, či už neexistuje poistenec s rovnakým emailom
        if (poistenecRepository.findByEmail(poistenecDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Poistenec s týmto emailom už existuje.");
        }

        // Konvertuje DTO na entitu a uloží do DB
        Poistenec poistenec = poistenecMapper.toEntity(poistenecDTO);
        poistenecRepository.save(poistenec);
    }

    /**
     * Načíta všetkých poistencov a vráti ich ako zoznam DTO.
     *
     * @return zoznam PoistenecDTO
     */
    @Override
    public List<PoistenecDTO> findAll() {
        return poistenecRepository.findAll().stream()
                .map(poistenecMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Nájde poistenca podľa ID.
     * Ak neexistuje, vyhodí PoistenecNotFoundException.
     *
     * @param id ID poistenca
     * @return PoistenecDTO pre dané ID
     * @throws PoistenecNotFoundException ak poistenec s daným ID neexistuje
     */
    @Override
    public PoistenecDTO findById(long id) {
        return poistenecRepository.findById(id)
                .map(poistenecMapper::toDto)
                .orElseThrow(() ->
                        new PoistenecNotFoundException(
                                "Poistenec s ID " + id + " neexistuje."
                        )
                );
    }

    /**
     * Aktualizuje existujúceho poistenca.
     * Najprv nájde entitu, potom ju aktualizuje dátami z DTO a uloží.
     *
     * @param poistenecDTO DTO s novými údajmi a existujúcim ID
     * @throws PoistenecNotFoundException ak poistenec s daným ID neexistuje
     */
    @Override
    @Transactional
    public void update(PoistenecDTO poistenecDTO) {
        // Načítanie existujúcej entity alebo vyhodenie výnimky
        Poistenec existingPoistenec = poistenecRepository.findById(poistenecDTO.getId())
                .orElseThrow(() ->
                        new PoistenecNotFoundException(
                                "Poistenec s ID " + poistenecDTO.getId() + " neexistuje."
                        )
                );

        // Skopírovanie zmien z DTO do entity
        poistenecMapper.updatePoistenec(poistenecDTO, existingPoistenec);

        // Uloženie aktualizovanej entity
        poistenecRepository.save(existingPoistenec);
    }

    /**
     * Odstráni poistenca podľa ID.
     *
     * @param id ID poistenca na vymazanie
     * @throws PoistenecNotFoundException ak poistenec s daným ID neexistuje
     */
    @Override
    @Transactional
    public void delete(long id) {
        // Overíme, či poistenec existuje pred jeho vymazaním
        if (!poistenecRepository.existsById(id)) {
            throw new PoistenecNotFoundException("Poistenec s ID " + id + " neexistuje.");
        }

        poistenecRepository.deleteById(id);
    }

    /**
     * Nájde poistenca podľa e-mailu alebo vyhodí výnimku.
     *
     * @param email e-mail poistenca
     * @return PoistenecDTO s údajmi daného e-mailu
     * @throws PoistenecNotFoundException ak poistenec s daným emailom neexistuje
     */
    @Override
    public PoistenecDTO findByEmail(String email) {
        return poistenecRepository.findByEmail(email)
                .map(poistenecMapper::toDto)
                .orElseThrow(() ->
                        new PoistenecNotFoundException(
                                "Poistenec s emailom " + email + " neexistuje."
                        )
                );
    }

    /**
     * Skontroluje, či poistencovi s daným ID patrí daný e-mail.
     * Používa sa pri autorizácii úprav/mazania zmlúv.
     *
     * @param poistenecId ID poistenca
     * @param email       email pre overenie vlastníctva
     * @return true, ak kombinácia ID a email existuje
     */
    @Override
    public boolean isOwnedByEmail(Long poistenecId, String email) {
        return poistenecRepository.existsByIdAndEmail(poistenecId, email);
    }
}