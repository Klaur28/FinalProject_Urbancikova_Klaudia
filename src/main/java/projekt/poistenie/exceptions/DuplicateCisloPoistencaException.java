package projekt.poistenie.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Výnimka, ktorá je vyvolaná pri pokuse o pridanie poistenca s číslom, ktoré už existuje.
 * Táto výnimka je mapovaná na HTTP status kód 409 CONFLICT, čo zabezpečuje
 * konzistentnú odpoveď pri volaní REST API.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateCisloPoistencaException extends RuntimeException {

    /**
     * Konštruktor s definovanou chybovou správou.
     *
     * @param message chybová správa popisujúca dôvod vyvolania výnimky
     */
    public DuplicateCisloPoistencaException(String message) {
        super(message);
    }
}