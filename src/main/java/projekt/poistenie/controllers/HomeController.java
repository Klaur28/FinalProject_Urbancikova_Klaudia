package projekt.poistenie.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller  // Označuje triedu ako Spring MVC kontrolér
public class HomeController {

    /**
     * Zobrazí domovskú stránku aplikácie.
     * HTTP GET požiadavka na URL "/"
     *
     * @return názov Thymeleaf šablóny "pages/home/index"
     */
    @GetMapping("/")
    public String renderIndex() {
        return "pages/home/index";
    }

    /**
     * Zobrazí informačnú stránku o projekte.
     * HTTP GET požiadavka na URL "/oprojekte"
     *
     * @return názov Thymeleaf šablóny "pages/oprojekte"
     */
    @GetMapping("/oprojekte")
    public String renderOProjekte() {
        return "pages/oprojekte";
    }

    @GetMapping("/contact")
    public String renderContact() {
        return "pages/home/contact";
    }
}
