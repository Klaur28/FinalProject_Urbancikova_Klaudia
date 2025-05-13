package projekt.poistenie.dtos;

import java.util.Collections;
import java.util.Map;

/**
 * Data Transfer Object (DTO) pre štatistické údaje o poistení.
 * Slúži na prenos agregovaných štatistických údajov z databázovej vrstvy
 * do prezentačnej vrstvy.
 */
public class StatistikyDTO {

    /**
     * Celkový počet poistených osôb v systéme.
     */
    private long pocetPoistenych;

    /**
     * Celkový počet poistných zmlúv v systéme.
     */
    private long pocetPoisteni;

    /**
     * Celkový počet poistných udalostí v systéme.
     */
    private long pocetUdalosti;

    /**
     * rozdelenie poistných zmlúv podľa typu poistenia.
     * Kľúč je typ poistenia,
     * hodnota je počet zmlúv daného typu.
     */
    private Map<String, Long> rozdeleniePodlaTypu;

    /**
     * Celkový počet aktívnych poistných zmlúv v systéme.
     */
    private long aktivneZmluvy;

    /**
     * Mapa znázorňujúca rozdelenie poistných zmlúv podľa ich stavu.
     * Kľúč je stav zmluvy (napr. "AKTIVNA", "CAKAJUCA", "UKONCENA"),
     * hodnota je počet zmlúv v danom stave.
     */
    private Map<String, Long> zmluvyPodlaStavu;

    /**
     * Hlavný konštruktor so všetkými parametrami.
     *
     * @param pocetPoistenych celkový počet poistených osôb
     * @param pocetPoisteni celkový počet poistných zmlúv
     * @param pocetUdalosti celkový počet poistných udalostí
     * @param rozdeleniePodlaTypu rozdelenie zmlúv podľa typu poistenia
     * @param aktivneZmluvy počet aktívnych zmlúv
     * @param zmluvyPodlaStavu rozdelenie zmlúv podľa stavu
     */
    public StatistikyDTO(long pocetPoistenych,
                         long pocetPoisteni,
                         long pocetUdalosti,
                         Map<String, Long> rozdeleniePodlaTypu,
                         long aktivneZmluvy,
                         Map<String, Long> zmluvyPodlaStavu) {
        this.pocetPoistenych = pocetPoistenych;
        this.pocetPoisteni = pocetPoisteni;
        this.pocetUdalosti = pocetUdalosti;
        this.rozdeleniePodlaTypu = rozdeleniePodlaTypu;
        this.aktivneZmluvy = aktivneZmluvy;
        this.zmluvyPodlaStavu = zmluvyPodlaStavu;
    }

    /**
     * Konštruktor so štyrmi parametrami.
     * Používa sa, keď nie sú k dispozícii údaje o stave zmlúv.
     *
     * @param pocetPoistenych celkový počet poistených osôb
     * @param pocetPoisteni celkový počet poistných zmlúv
     * @param pocetUdalosti celkový počet poistných udalostí
     * @param rozdeleniePodlaTypu rozdelenie zmlúv podľa typu poistenia
     */
    public StatistikyDTO(long pocetPoistenych,
                         long pocetPoisteni,
                         long pocetUdalosti,
                         Map<String, Long> rozdeleniePodlaTypu) {
        this(pocetPoistenych,
                pocetPoisteni,
                pocetUdalosti,
                rozdeleniePodlaTypu,
                0,
                Collections.emptyMap());
    }

    /**
     * Konštruktor s tromi základnými parametrami.
     * Používa sa, keď nie sú k dispozícii údaje o rozdelení a stave zmlúv.
     *
     * @param pocetPoistenych celkový počet poistených osôb
     * @param pocetPoisteni celkový počet poistných zmlúv
     * @param pocetUdalosti celkový počet poistných udalostí
     */
    public StatistikyDTO(long pocetPoistenych,
                         long pocetPoisteni,
                         long pocetUdalosti) {
        this(pocetPoistenych,
                pocetPoisteni,
                pocetUdalosti,
                Collections.emptyMap());
    }

    // Gettery

    /**
     * Získa celkový počet poistených osôb.
     *
     * @return počet poistených osôb
     */
    public long getPocetPoistenych() {
        return pocetPoistenych;
    }

    /**
     * Získa celkový počet poistných zmlúv.
     *
     * @return počet poistných zmlúv
     */
    public long getPocetPoisteni() {
        return pocetPoisteni;
    }

    /**
     * Získa celkový počet poistných udalostí.
     *
     * @return počet poistných udalostí
     */
    public long getPocetUdalosti() {
        return pocetUdalosti;
    }

    /**
     * Získa rozdelenie poistných zmlúv podľa typu poistenia.
     *
     * @return mapa s rozdelením zmlúv podľa typu
     */
    public Map<String, Long> getRozdeleniePodlaTypu() {
        return rozdeleniePodlaTypu;
    }

    /**
     * Získa počet aktívnych poistných zmlúv.
     *
     * @return počet aktívnych zmlúv
     */
    public long getAktivneZmluvy() {
        return aktivneZmluvy;
    }

    /**
     * Získa rozdelenie poistných zmlúv podľa ich stavu.
     *
     * @return mapa s rozdelením zmlúv podľa stavu
     */
    public Map<String, Long> getZmluvyPodlaStavu() {
        return zmluvyPodlaStavu;
    }
}