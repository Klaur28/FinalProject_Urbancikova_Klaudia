package projekt.poistenie.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import projekt.poistenie.dtos.PoistnaZmluvaDTO;
import projekt.poistenie.entities.RelationType;
import projekt.poistenie.exceptions.AccessDeniedException;
import projekt.poistenie.service.PoistnaZmluvaService;
import projekt.poistenie.service.PoistenecService;

import java.util.Collections;

/**
 * Kontrolér pre správu poistných zmlúv - zabezpečuje zobrazovanie zoznamu zmlúv,
 * vytváranie nových, úpravu a mazanie existujúcich zmlúv.
 */
@Controller
@RequestMapping("/poistnezmluvy")
public class PoistnaZmluvaController {

    /**
     * Služba pre prácu s poistnými zmluvami.
     */
    private final PoistnaZmluvaService poistnaZmluvaService;

    /**
     * Služba pre prácu s poistencami - potrebná pre získanie údajov o poistencoch
     * pri vytváraní a úprave poistných zmlúv.
     */
    private final PoistenecService poistenecService;

    /**
     * Konštruktor pre vloženie závislostí (dependency injection).
     *
     * @param poistnaZmluvaService služba pre operácie s poistnými zmluvami
     * @param poistenecService služba pre operácie s poistencami
     */
    @Autowired
    public PoistnaZmluvaController(PoistnaZmluvaService poistnaZmluvaService,
                                   PoistenecService poistenecService) {
        this.poistnaZmluvaService = poistnaZmluvaService;
        this.poistenecService = poistenecService;
    }

    /**
     * Kontroluje, či má používateľ administrátorskú rolu.
     *
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @return true ak má používateľ rolu ADMIN, inak false
     */
    private boolean hasAdminRole(Authentication authentication) {
        return authentication != null &&
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    /**
     * Získa email prihláseného používateľa.
     *
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @return email používateľa alebo null, ak nie je prihlásený
     */
    private String getUserEmail(Authentication authentication) {
        return authentication != null ? authentication.getName() : null;
    }

    /**
     * Pripraví model pre zobrazenie vo formulári - pridá dostupných poistencov
     * podľa oprávnení používateľa.
     *
     * @param model model pre Thymeleaf šablónu
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @param viewName názov pohľadu, pre ktorý sa model pripravuje (novy/oprav)
     */
    private void prepareModel(Model model, Authentication authentication, String viewName) {
        boolean isAdmin = hasAdminRole(authentication);
        String userEmail = getUserEmail(authentication);

        // Pridanie typov vzťahov do modelu pre všetky pohľady
        model.addAttribute("relations", RelationType.values());

        // Pre formuláre vytvorenia a úpravy pridať zoznam dostupných poistencov
        if ("novy".equals(viewName) || "oprav".equals(viewName)) {
            if (isAdmin) {
                // Administrátor vidí všetkých poistencov
                model.addAttribute("poistenci", poistenecService.findAll());
            } else {
                try {
                    // Bežný používateľ vidí len seba
                    model.addAttribute("poistenci",
                            Collections.singletonList(poistenecService.findByEmail(userEmail)));
                } catch (EntityNotFoundException exception) {
                    // Používateľ nemá vytvorený profil poistenca
                    model.addAttribute("poistenci", Collections.emptyList());
                    model.addAttribute("warning",
                            "Nemáte vytvorený žiadny profil poistenca. " +
                                    "Prosím, najprv si vytvorte profil poistenca.");
                }
            }
        }
    }

    /**
     * Spracováva chyby validácie formulára - pripraví model a vráti správny pohľad.
     *
     * @param bindingResult výsledky validácie
     * @param model model pre Thymeleaf šablónu
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @param viewName názov pohľadu, kde sa zobrazia chyby
     * @return cesta k pohľadu s formulárom
     */
    private String handleBindingErrors(BindingResult bindingResult, Model model, Authentication authentication, String viewName) {
        prepareModel(model, authentication, viewName);
        return "pages/poistenie/" + viewName;
    }

    /**
     * Zobrazenie zoznamu poistných zmlúv.
     *
     * @param model model pre Thymeleaf šablónu
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @return cesta k šablóne so zoznamom zmlúv
     */
    @GetMapping
    public String listAll(Model model, Authentication authentication) {
        boolean isAdmin = hasAdminRole(authentication);
        String userEmail = getUserEmail(authentication);

        try {
            // Rozlíšenie oprávnení - admin vidí všetky zmluvy, bežný používateľ len svoje
            model.addAttribute("zmluvy", isAdmin ? poistnaZmluvaService.findAll() : poistnaZmluvaService.findByPoistenecEmail(userEmail));
        } catch (EntityNotFoundException exception) {
            // Používateľ nemá vytvorený profil poistenca alebo žiadne zmluvy
            model.addAttribute("zmluvy", Collections.emptyList());
            model.addAttribute("warning",
                    "Nemáte vytvorený žiadny profil poistenca. " +
                            "Prosím, najprv si vytvorte profil poistenca.");
        }

        return "pages/poistenie/zoznam";
    }
    /**
     * Zobrazenie formulára pre vytvorenie novej poistnej zmluvy.
     *
     * @param model model pre Thymeleaf šablónu
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @return cesta k šablóne s formulárom
     */
    @GetMapping("/novy")
    public String showCreateForm(Model model, Authentication authentication) {
        prepareModel(model, authentication, "novy");
        model.addAttribute("poistnaZmluva", new PoistnaZmluvaDTO());
        return "pages/poistenie/novy";
    }
    /**
     * Spracovanie formulára pre vytvorenie novej poistnej zmluvy.
     *
     * @param poistnaZmluvaDTO dáta z formulára
     * @param bindingResult výsledky validácie
     * @param model model pre Thymeleaf šablónu
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @return presmerovanie na zoznam zmlúv alebo späť na formulár pri chybe
     */
    @PostMapping
    public String saveNew(@Valid @ModelAttribute("poistnaZmluva") PoistnaZmluvaDTO poistnaZmluvaDTO,
                          BindingResult bindingResult,
                          Model model,
                          Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return handleBindingErrors(bindingResult, model, authentication, "novy");
        }
        try {
            // Kontrola oprávnení - bežný používateľ môže vytvoriť zmluvy len pre seba
            if (!hasAdminRole(authentication) &&
                    !poistenecService.isOwnedByEmail(poistnaZmluvaDTO.getPoistenecId(), getUserEmail(authentication))) {
                throw new AccessDeniedException("Nemáte oprávnenie vytvoriť poistenie pre iného používateľa.");
            }

            poistnaZmluvaService.create(poistnaZmluvaDTO);
            return "redirect:/poistnezmluvy";
        } catch (Exception exception) {
            model.addAttribute("error", exception.getMessage());
            return handleBindingErrors(bindingResult, model, authentication, "novy");
        }
    }

    /**
     * Zobrazenie detailu poistnej zmluvy.
     *
     * @param id ID poistnej zmluvy, ktorej detail sa má zobraziť
     * @param model model pre Thymeleaf šablónu
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @return cesta k šablóne s detailom zmluvy alebo chybová stránka pri nedostatočných oprávneniach
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, Authentication authentication) {
        try {
            // Kontrola oprávnení
            if (!hasAccessToZmluva(id, authentication)) {
                return "pages/error/access-denied";
            }

            // Pridanie poistnej zmluvy do modelu
            model.addAttribute("poistenie", poistnaZmluvaService.findById(id));
            return "pages/poistenie/detail";
        } catch (EntityNotFoundException exception) {
            model.addAttribute("error", "Zmluva nebola nájdená: " + exception.getMessage());
            return "error";
        }
    }

    /**
     * Zobrazenie formulára pre úpravu poistnej zmluvy.
     *
     * @param id ID poistnej zmluvy, ktorá sa má upraviť
     * @param model model pre Thymeleaf šablónu
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @return cesta k šablóne s formulárom alebo chybová stránka pri nedostatočných oprávneniach
     */
    @GetMapping("/{id}/oprav")
    public String showEditForm(@PathVariable Long id, Model model, Authentication authentication) {
        try {
            // Kontrola oprávnení
            if (!hasAccessToZmluva(id, authentication)) {
                return "pages/error/access-denied";
            }

            // Pridanie poistnej zmluvy a dostupných poistencov do modelu
            model.addAttribute("poistnaZmluva", poistnaZmluvaService.findById(id));
            prepareModel(model, authentication, "oprav");
            return "pages/poistenie/oprav";
        } catch (EntityNotFoundException exception) {
            model.addAttribute("error", "Zmluva nebola nájdená: " + exception.getMessage());
            return "error";
        }
    }

    /**
     * Spracovanie formulára pre úpravu poistnej zmluvy.
     *
     * @param id ID poistnej zmluvy, ktorá sa má upraviť
     * @param poistnaZmluvaDTO dáta z formulára
     * @param bindingResult výsledky validácie
     * @param model model pre Thymeleaf šablónu
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @return presmerovanie na zoznam zmlúv alebo späť na formulár pri chybe
     */
    @PostMapping("/{id}/oprav")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("poistnaZmluva") PoistnaZmluvaDTO poistnaZmluvaDTO,
                         BindingResult bindingResult,
                         Model model,
                         Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return handleBindingErrors(bindingResult, model, authentication, "oprav");
        }

        try {
            // Kontrola oprávnení
            if (!hasAccessToZmluva(id, authentication)) {
                return "pages/error/access-denied";
            }

            // Aktualizácia poistnej zmluvy
            poistnaZmluvaDTO.setId(id);
            poistnaZmluvaService.update(poistnaZmluvaDTO);
            return "redirect:/poistnezmluvy";
        } catch (Exception exception) {
            model.addAttribute("error", exception.getMessage());
            return handleBindingErrors(bindingResult, model, authentication, "oprav");
        }
    }
    /**
     * Vymazanie poistnej zmluvy.
     *
     * @param id ID poistnej zmluvy, ktorá sa má vymazať
     * @param model model pre Thymeleaf šablónu
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @return presmerovanie na zoznam zmlúv alebo chybová stránka pri nedostatočných oprávneniach
     */
    @PostMapping("/{id}/vymaz")
    public String delete(@PathVariable Long id,
                         Model model,
                         Authentication authentication) {

        try {
            // Kontrola oprávnení
            if (!hasAccessToZmluva(id, authentication)) {
                return "pages/error/access-denied";
            }

            // Vymazanie poistnej zmluvy
            poistnaZmluvaService.delete(id);
            return "redirect:/poistnezmluvy";
        } catch (Exception exception) {
            model.addAttribute("error", "Nastala chyba pri vymazávaní: " + exception.getMessage());
            return "error";
        }
    }
    /**
     * Kontroluje, či má používateľ prístup k danej poistnej zmluve.
     * Administrátor má prístup ku všetkým zmluvám, bežný používateľ len k svojim.
     *
     * @param zmluvaId ID poistnej zmluvy
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @return true ak má používateľ oprávnenie, inak false
     */
    private boolean hasAccessToZmluva(Long zmluvaId, Authentication authentication) {
        return hasAdminRole(authentication) || poistnaZmluvaService.belongsToEmail(zmluvaId, getUserEmail(authentication));
    }
}