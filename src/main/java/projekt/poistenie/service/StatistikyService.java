package projekt.poistenie.service;

import projekt.poistenie.dtos.StatistikyDTO;

/**
 * Služba pre získavanie štatistických údajov o systéme poistenia.
 * Poskytuje metódy na agregáciu a analýzu dát o poistencoch, poistných zmluvách
 * a poistných udalostiach.
 */
public interface StatistikyService {

    /**
     * Získa globálne štatistiky celého systému.
     * Zahŕňa počty poistencov, poistných zmlúv, poistných udalostí,
     * a rôzne agregované dáta ako rozdelenie zmlúv podľa typu a stavu.
     *
     * @return objekt StatistikyDTO obsahujúci agregované štatistické údaje
     */
    StatistikyDTO getGlobalStatistics();
}