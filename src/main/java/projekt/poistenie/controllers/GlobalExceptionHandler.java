package projekt.poistenie.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.exceptions.TemplateInputException;

/**
 * Globálny handler výnimiek pre celú aplikáciu.
 * Umožňuje centralizované spracovanie výnimiek pre všetky kontroléry.
 * Trieda je označená anotáciou @ControllerAdvice, čo znamená, že metódy
 * s anotáciou @ExceptionHandler budú platné pre všetky kontroléry v aplikácii.
 * Tento prístup zlepšuje jednotnosť spracovania chýb a znižuje duplicitný kód.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Spracováva výnimku TemplateInputException, ktorá nastáva pri problémoch
     * so šablónami Thymeleaf. Táto výnimka môže vzniknúť aj keď šablóna
     * nebola nájdená, alebo obsahuje neplatný syntax.
     *
     * Metóda presmeruje používateľa na generickú chybovú stránku s informáciou
     * o probléme so šablónou.
     *
     * @param ex zachytená výnimka obsahujúca detaily o probléme
     * @param model model pre Thymeleaf šablónu, do ktorého sa pridáva informácia o chybe
     * @return cesta ku generickej chybovej šablóne
     */
    @ExceptionHandler(TemplateInputException.class)
    public String handleMissingTemplate(Exception ex, Model model) {
        // Pridanie detailnej chybovej správy do modelu pre zobrazenie používateľovi
        model.addAttribute("error", "Chýbajúca šablóna: " + ex.getMessage());

        // Presmerovanie na generickú chybovú stránku
        return "pages/error/generic";
    }
}