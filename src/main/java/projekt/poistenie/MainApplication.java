package projekt.poistenie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hlavná aplikačná trieda pre systém Evidencie Poistenia.
 * Spúšťa Spring Boot aplikáciu a konfiguruje základné komponenty.
 *
 * Anotácia @SpringBootApplication kombinuje:
 * - @Configuration: označuje triedu ako zdroj definícií bean-ov
 * - @EnableAutoConfiguration: zapína automatickú konfiguráciu na základe závislostí
 * - @ComponentScan: umožňuje hľadanie Spring komponentov v definovaných balíčkoch
 *
 * Anotácia @EnableJpaRepositories aktivuje JPA repozitáre pre prácu s databázou.
 */
@SpringBootApplication
@EnableJpaRepositories
public class MainApplication {

    /**
     * Hlavná metóda aplikácie, ktorá sa spúšťa pri štarte programu.
     * Inicializuje Spring Boot aplikáciu s konfiguráciou definovanou v tejto triede.
     *
     * @param arguments argumenty príkazového riadka odovzdané aplikácii pri spustení
     */
    public static void main(String[] arguments) {
        // Spustenie Spring Boot aplikácie
        SpringApplication.run(MainApplication.class, arguments);
    }
}