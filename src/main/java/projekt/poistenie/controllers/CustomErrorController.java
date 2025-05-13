package projekt.poistenie.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * Kontrolér pre spracovanie chybových stavov aplikácie.
 * Implementuje rozhranie ErrorController, čím preberá zodpovednosť
 * za obsluhu všetkých chýb, ktoré nie sú zachytené inými kontrolérmi
 */
@Controller
public class CustomErrorController implements ErrorController {

    /**
     * Objekt pre prístup k atribútom chyby. Obsahuje detailné informácie
     * o vzniknutej chybe vrátane typu, správy a stack trace.
     */
    private final ErrorAttributes errorAttributes;

    /**
     * Konštruktor pre injection ErrorAttributes objektu.
     *
     * @param errorAttributes objekt poskytujúci informácie o chybe
     */
    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    /**
     * Spracováva požiadavky na endpoint /error, ktorý je štandardným
     * cieľom pre chybové presmerovania v Spring Boot aplikácii.
     * Extrahuje detaily o chybe a pripravuje ich pre zobrazenie v chybovej šablóne.
     *
     * @param request HTTP požiadavka, ktorá obsahuje pôvodné informácie o chybe
     * @param model model pre Thymeleaf šablónu, do ktorého sa pridávajú atribúty
     * @return cesta k chybovej šablóne, ktorá bude použitá na zobrazenie chyby
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Konverzia HttpServletRequest na WebRequest pre prácu s errorAttributes
        WebRequest webRequest = new ServletWebRequest(request);

        // Získanie základných atribútov chyby bez stack trace pre bezpečnosť
        Map<String, Object> errorAttributes = getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());

        // Pridanie relevantných informácií o chybe do modelu pre šablónu
        model.addAttribute("timestamp", errorAttributes.get("timestamp")); // Čas, kedy chyba nastala
        model.addAttribute("error", errorAttributes.get("error"));         // Typ chyby (napr. "Not Found")
        model.addAttribute("message", errorAttributes.get("message"));     // Detailná správa o chybe
        model.addAttribute("path", errorAttributes.get("path"));           // URL, ktorá spôsobila chybu
        model.addAttribute("status", errorAttributes.get("status"));       // HTTP status kód (napr. 404, 500)

        // Nastavenie aktívnej položky menu na prázdnu hodnotu, pretože chybová stránka
        // nekorešponduje so žiadnou položkou v navigačnom menu
        model.addAttribute("activeMenu", "");

        // Vrátenie cesty k chybovej šablóne, ktorá zobrazí používateľovi chybovú stránku
        return "pages/error";
    }

    /**
     * Pomocná metóda pre získanie atribútov chyby z WebRequest.
     * Deleguje zodpovednosť za získanie atribútov na injektovaný ErrorAttributes objekt.
     *
     * @param webRequest požiadavka obsahujúca informácie o chybe
     * @param options nastavenia určujúce, ktoré atribúty chyby sa majú zahrnúť
     * @return mapa obsahujúca všetky dostupné atribúty chyby podľa zadaných options
     */
    private Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        return errorAttributes.getErrorAttributes(webRequest, options);
    }
}