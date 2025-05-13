package projekt.poistenie.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Výnimka, ktorá sa vyhodí, keď sa hľadaný poistenec nenájde.
 * Vďaka anotácii @ResponseStatus vráti HTTP stav 404 (Not Found).
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PoistenecNotFoundException extends RuntimeException {

    /**
     * Vytvorí výnimku s vlastnou chybovou správou.
     *
     * @param message text, ktorý popisuje, prečo poistenec neexistuje
     */
    public PoistenecNotFoundException(String message) {
        super(message);
    }
}
