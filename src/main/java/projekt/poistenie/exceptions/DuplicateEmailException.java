package projekt.poistenie.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Výnimka, ktorá je vyvolaná pri pokuse o pridanie používateľa s emailom,
 * ktorý už existuje v systéme.
 * Táto výnimka slúži na ochranu pred duplicitnými emailovými adresami.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEmailException extends RuntimeException {

    /**
     * Konštruktor s predvolenou chybovou správou.
     */
    public DuplicateEmailException() {
        super("Email už existuje v systéme");
    }

    /**
     * Konštruktor s definovanou chybovou správou.
     *
     * @param message chybová správa popisujúca dôvod vyvolania výnimky
     */
    public DuplicateEmailException(String message) {
        super(message);
    }
}