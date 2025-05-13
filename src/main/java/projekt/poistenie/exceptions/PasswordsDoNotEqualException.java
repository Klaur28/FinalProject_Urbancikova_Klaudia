package projekt.poistenie.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Výnimka, ktorá je vyvolaná keď zadané heslo a jeho potvrdenie sa nezhodujú.
 * Táto výnimka sa zvyčajne vyvoláva pri registrácii používateľa alebo zmene hesla,
 * keď používateľ zadá rozdielne hodnoty v poliach "heslo" a "potvrďte heslo".
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordsDoNotEqualException extends RuntimeException {

    /**
     * Konštruktor s predvolenou chybovou správou.
     */
    public PasswordsDoNotEqualException() {
        super("Zadané heslá sa nezhodujú");
    }

    /**
     * Konštruktor s definovanou chybovou správou.
     *
     * @param message chybová správa popisujúca dôvod vyvolania výnimky
     */
    public PasswordsDoNotEqualException(String message) {
        super(message);
    }
}