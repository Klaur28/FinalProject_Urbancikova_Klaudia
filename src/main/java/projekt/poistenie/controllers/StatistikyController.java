package projekt.poistenie.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projekt.poistenie.dtos.StatistikyDTO;
import projekt.poistenie.service.StatistikyService;

/**
 * Kontrolér na spracovanie požiadaviek súvisiacich so štatistikami.
 */
@Controller
public class StatistikyController {

    private final StatistikyService statistikyService;

    /**
     * Konštruktor na vkladanie služby StatistikyService.
     *
     * @param statistikyService služba na načítanie štatistík
     */
    public StatistikyController(StatistikyService statistikyService) {
        this.statistikyService = statistikyService;
    }

    /**
     * Spracováva požiadavky GET na „/statistiky“.
     * Načítava globálne štatistiky a pridáva ich do modelu na vykreslenie.
     *
     * @param model je na prenos údajov do zobrazenia
     * @return šablóny Thymeleaf pre štatistiky
     */
    @GetMapping("/statistiky")
    public String renderStatistics(Model model) {
        try {
            StatistikyDTO statistics = statistikyService.getGlobalStatistics();

            model.addAttribute("pocetPoistenych", statistics.getPocetPoistenych());
            model.addAttribute("pocetPoisteni", statistics.getPocetPoisteni());
            model.addAttribute("pocetUdalosti", statistics.getPocetUdalosti());
            model.addAttribute("rozdeleniePodlaTypu", statistics.getRozdeleniePodlaTypu());

            return "pages/statistiky/statistiky";
        } catch (Exception e) {
            model.addAttribute("error", "Nepodarilo sa načítať štatistiky: " + e.getMessage());
            return "pages/error";
        }
    }
}