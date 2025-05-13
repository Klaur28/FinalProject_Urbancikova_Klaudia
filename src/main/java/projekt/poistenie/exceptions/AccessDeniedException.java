package projekt.poistenie.exceptions;

/**
 * Výnimka pre prípady, keď používateľ nemá dostatočné oprávnenia na danú operáciu.
 */
public class AccessDeniedException extends RuntimeException {

    /**
     * Vytvorí novú výnimku s danou správou.
     *
     * @param message detailná správa
     */
    public AccessDeniedException(String message) {
        super(message);
    }

    /**
     * Vytvorí novú výnimku s danou správou a príčinou.
     *
     * @param message detailná správa
     * @param cause príčina výnimky
     */
    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}