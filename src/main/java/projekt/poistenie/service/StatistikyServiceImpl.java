package projekt.poistenie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projekt.poistenie.dtos.StatistikyDTO;
import projekt.poistenie.entities.PoistnaZmluva;
import projekt.poistenie.repository.PoistenecRepository;
import projekt.poistenie.repository.PoistnaZmluvaRepository;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementácia služby pre získavanie štatistických údajov.
 * Agreguje dáta z rôznych repository a pripravuje ich na zobrazenie.
 */
@Service
@RequiredArgsConstructor
public class StatistikyServiceImpl implements StatistikyService {

    /**
     * Repository pre prístup k dátam poistencov.
     */
    private final PoistenecRepository poistenecRepo;

    /**
     * Repository pre prístup k dátam poistných zmlúv.
     */
    private final PoistnaZmluvaRepository zmluvaRepo;

    /**
     * Získanie globálnych štatistík zo systému.
     * Metóda je označená ako @Transactional(readOnly = true), čo optimalizuje
     * databázový prístup, keďže ide len o čítanie dát bez modifikácie.
     *
     * @return objekt StatistikyDTO s agregovanými údajmi
     */
    @Override
    @Transactional(readOnly = true)
    public StatistikyDTO getGlobalStatistics() {
        // Základné počty entít
        long pocetPoistenych = poistenecRepo.count();
        long pocetPoisteni = zmluvaRepo.count();

        // Načítanie všetkých zmlúv - optimalizované na jedno načítanie z databázy
        final LocalDate dnesnyDatum = LocalDate.now();
        final var vsetkyZmluvy = zmluvaRepo.findAll();

        // Počet udalostí - spočítame pre každú zmluvu
        long pocetUdalosti = vsetkyZmluvy.stream()
                .mapToLong(z -> z.getUdalosti() != null ? z.getUdalosti().size() : 0)
                .sum();

        // Rozdelenie poistných zmlúv podľa typu
        Map<String, Long> rozdeleniePodlaTypu = vsetkyZmluvy.stream()
                .collect(Collectors.groupingBy(
                        PoistnaZmluva::getDruhPoistenia,
                        Collectors.counting()
                ));

        // Aktívne zmluvy - počet zmlúv s platnosťou zahŕňajúcou dnešný dátum
        long aktivneZmluvy = vsetkyZmluvy.stream()
                .filter(z -> dnesnyDatum.compareTo(z.getPlatnostOd()) >= 0 &&
                        dnesnyDatum.compareTo(z.getPlatnostDo()) <= 0)
                .count();

        // Rozdelenie podľa stavu zmlúv (aktívne, čakajúce, ukončené, expirované)
        Map<String, Long> zmluvyPodlaStavu = vsetkyZmluvy.stream()
                .collect(Collectors.groupingBy(
                        zmluva -> {
                            if (dnesnyDatum.isBefore(zmluva.getPlatnostOd())) {
                                return "CAKAJUCA"; // Zmluva ešte nezačala platiť
                            } else if (dnesnyDatum.isAfter(zmluva.getPlatnostDo())) {
                                return "EXPIROVANA"; // Zmluva už skončila
                            } else {
                                return "AKTIVNA"; // Zmluva je aktívna
                            }
                        },
                        Collectors.counting()
                ));

        // Vytvorenie kompletného DTO so všetkými štatistikami
        return new StatistikyDTO(
                pocetPoistenych,
                pocetPoisteni,
                pocetUdalosti,
                rozdeleniePodlaTypu,
                aktivneZmluvy,
                zmluvyPodlaStavu
        );
    }
}