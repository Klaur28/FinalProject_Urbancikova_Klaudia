package projekt.poistenie.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projekt.poistenie.dtos.PoistnaZmluvaDTO;
import projekt.poistenie.dtos.mappers.PoistnaZmluvaMapper;
import projekt.poistenie.entities.Poistenec;
import projekt.poistenie.entities.PoistnaZmluva;
import projekt.poistenie.repository.PoistenecRepository;
import projekt.poistenie.repository.PoistnaZmluvaRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementácia služby pre správu poistných zmlúv.
 * Poskytuje biznis logiku a operácie nad poistnými zmluvami.
 */
@Service
public class PoistnaZmluvaServiceImpl implements PoistnaZmluvaService {

    private final PoistnaZmluvaRepository poistnaZmluvaRepository;
    private final PoistenecRepository poistenecRepository;
    private final PoistnaZmluvaMapper poistnaZmluvaMapper;

    /**
     * Konštruktor pre injection závislostí.
     *
     * @param poistnaZmluvaRepository repozitár pre operácie so zmluvami
     * @param poistenecRepository repozitár pre operácie s poistencami
     * @param poistnaZmluvaMapper mapper pre konverziu medzi DTO a entitou
     */
    @Autowired
    public PoistnaZmluvaServiceImpl(PoistnaZmluvaRepository poistnaZmluvaRepository,
                                    PoistenecRepository poistenecRepository,
                                    PoistnaZmluvaMapper poistnaZmluvaMapper) {
        this.poistnaZmluvaRepository = poistnaZmluvaRepository;
        this.poistenecRepository = poistenecRepository;
        this.poistnaZmluvaMapper = poistnaZmluvaMapper;
    }

    /**
     * Vytvorí novú poistnú zmluvu.
     *
     * @param poistnaZmluvaDTO dáta novej zmluvy
     * @throws IllegalArgumentException ak ID poistenca nie je zadané
     * @throws EntityNotFoundException ak poistenec neexistuje
     */
    @Override
    @Transactional
    public void create(PoistnaZmluvaDTO poistnaZmluvaDTO) {
        if (poistnaZmluvaDTO.getPoistenecId() == null) {
            throw new IllegalArgumentException("ID poistenca nemôže byť null");
        }

        // Konverzia DTO na entitu
        PoistnaZmluva poistnaZmluva = poistnaZmluvaMapper.toEntity(poistnaZmluvaDTO);

        // Nájdenie a nastavenie poistenca
        Poistenec poistenec = poistenecRepository.findById(poistnaZmluvaDTO.getPoistenecId())
                .orElseThrow(() -> new EntityNotFoundException("Poistenec s ID " +
                        poistnaZmluvaDTO.getPoistenecId() + " nebol nájdený"));

        poistnaZmluva.setPoistenec(poistenec);

        // Uloženie entity do databázy
        poistnaZmluvaRepository.save(poistnaZmluva);
    }

    /**
     * Načíta všetky poistné zmluvy.
     *
     * @return zoznam všetkých zmlúv
     */
    @Override
    public List<PoistnaZmluvaDTO> findAll() {
        return poistnaZmluvaRepository.findAll().stream()
                .map(poistnaZmluvaMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Nájde poistnú zmluvu podľa ID.
     *
     * @param id ID poistnej zmluvy
     * @return DTO poistnej zmluvy
     * @throws EntityNotFoundException ak zmluva neexistuje
     */
    @Override
    public PoistnaZmluvaDTO findById(Long id) {
        PoistnaZmluva poistnaZmluva = poistnaZmluvaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Poistná zmluva s ID " + id + " nebola nájdená"));

        return poistnaZmluvaMapper.toDto(poistnaZmluva);
    }

    /**
     * Aktualizuje existujúcu poistnú zmluvu.
     *
     * @param poistnaZmluvaDTO dáta na aktualizáciu
     * @throws EntityNotFoundException ak zmluva neexistuje
     */
    @Override
    @Transactional
    public void update(PoistnaZmluvaDTO poistnaZmluvaDTO) {
        // Nájdenie existujúcej entity
        PoistnaZmluva existingPoistnaZmluva = poistnaZmluvaRepository.findById(poistnaZmluvaDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Poistná zmluva s ID " +
                        poistnaZmluvaDTO.getId() + " neexistuje"));

        // Aktualizácia entity novými údajmi
        poistnaZmluvaMapper.updatePoistnaZmluva(poistnaZmluvaDTO, existingPoistnaZmluva);

        // Uloženie aktualizovanej entity
        poistnaZmluvaRepository.save(existingPoistnaZmluva);
    }

    /**
     * Odstráni poistnú zmluvu.
     *
     * @param id ID poistnej zmluvy na odstránenie
     * @throws EntityNotFoundException ak zmluva neexistuje
     */
    @Override
    @Transactional
    public void delete(Long id) {
        // Overenie existencie pred vymazaním
        if (!poistnaZmluvaRepository.existsById(id)) {
            throw new EntityNotFoundException("Poistná zmluva s ID " + id + " neexistuje");
        }

        poistnaZmluvaRepository.deleteById(id);
    }

    /**
     * Alias pre metódu findAll().
     *
     * @return zoznam všetkých zmlúv
     */
    @Override
    public List<PoistnaZmluvaDTO> getAll() {
        return findAll();
    }

    /**
     * Alias pre metódu findById().
     *
     * @param id ID poistnej zmluvy
     * @return DTO poistnej zmluvy
     */
    @Override
    public PoistnaZmluvaDTO getById(long id) {
        return findById(id);
    }

    /**
     * Alias pre metódu update().
     *
     * @param poistnaZmluvaDTO dáta na aktualizáciu
     */
    @Transactional
    @Override
    public void edit(PoistnaZmluvaDTO poistnaZmluvaDTO) {
        update(poistnaZmluvaDTO);
    }

    /**
     * Nájde poistné zmluvy pre poistenca s daným emailom.
     *
     * @param email email poistenca
     * @return zoznam poistných zmlúv poistenca
     * @throws EntityNotFoundException ak poistenec neexistuje
     */
    @Override
    public List<PoistnaZmluvaDTO> findByPoistenecEmail(String email) {
        Poistenec poistenec = poistenecRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Poistenec s emailom " + email + " neexistuje."));

        return poistnaZmluvaRepository.findByPoistenec(poistenec).stream()
                .map(poistnaZmluvaMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Overí, či poistná zmluva patrí používateľovi s daným emailom.
     *
     * @param zmluvaId ID poistnej zmluvy
     * @param email email používateľa
     * @return true ak zmluva patrí danému používateľovi
     */
    @Override
    public boolean belongsToEmail(Long zmluvaId, String email) {
        return poistnaZmluvaRepository.existsByIdAndPoistenecEmail(zmluvaId, email);
    }
}