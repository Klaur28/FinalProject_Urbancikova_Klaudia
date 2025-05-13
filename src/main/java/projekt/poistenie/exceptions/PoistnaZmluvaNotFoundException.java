package projekt.poistenie.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Výnimka, ktorá je vyvolaná, keď poistná zmluva s daným identifikátorom
 * nie je nájdená v databáze.
 * Táto výnimka sa zvyčajne vyvoláva pri pokuse o prístup, úpravu alebo
 * odstránenie neexistujúcej poistnej zmluvy.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PoistnaZmluvaNotFoundException extends RuntimeException {

    /**
     * Konštruktor s predvolenou chybovou správou.
     */
    public PoistnaZmluvaNotFoundException() {
        super("Poistná zmluva nenájdená");
    }

    /**
     * Konštruktor s definovanou chybovou správou.
     *
     * @param message chybová správa popisujúca dôvod vyvolania výnimky
     */
    public PoistnaZmluvaNotFoundException(String message) {
        super(message);
    }

    /**
     * Konštruktor s definovanou chybovou správou a pôvodnou výnimkou.
     *
     * @param message chybová správa popisujúca dôvod vyvolania výnimky
     * @param cause pôvodná výnimka, ktorá spôsobila túto výnimku
     */
    public PoistnaZmluvaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}