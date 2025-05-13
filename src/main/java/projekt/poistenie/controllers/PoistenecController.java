package projekt.poistenie.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projekt.poistenie.dtos.PoistenecDTO;
import projekt.poistenie.entities.RelationType;
import projekt.poistenie.exceptions.PoistenecNotFoundException;
import projekt.poistenie.service.PoistenecService;

import java.util.Collections;

/**
 * Kontrolér pre správu poistencov - umožňuje zobrazovanie, vytváranie, úpravu a mazanie.
 */
@Controller
@RequestMapping("/poistenci")
public class PoistenecController {

    /**
     * Služba pre prácu s poistencami, obsahuje biznis logiku.
     */
    @Autowired
    private PoistenecService poistenecService;

    /**
     * Pridáva spoločné atribúty pre všetky metódy kontroléra.
     * Táto metóda sa vykoná pred každou metódou kontroléra.
     *
     * @param model model pre Thymeleaf šablónu
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        // Pridanie zoznamu typov vzťahov do modelu, ktoré sú potrebné vo formulároch
        model.addAttribute("relations", RelationType.values());
    }

    /**
     * Zobrazenie zoznamu poistencov.
     * Administrátor vidí všetkých poistencov, bežný používateľ len svoje záznamy.
     *
     * @param model model pre Thymeleaf šablónu
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @return cesta k šablóne so zoznamom poistencov
     */
    @GetMapping
    public String list(Model model, Authentication authentication) {
        try {
            // Rozlíšenie oprávnení - admin vidí všetkých, bežný používateľ len seba
            model.addAttribute("poistenci", isAdmin(authentication) ?
                    poistenecService.findAll() :
                    Collections.singletonList(poistenecService.findByEmail(authentication.getName())));
        } catch (Exception exception) {
            // V prípade chyby (napr. používateľ nemá vytvorený profil poistenca)
            model.addAttribute("poistenci", Collections.emptyList());
            model.addAttribute("error", "Nemáte poistenie.");
        }
        return "pages/poistenec/zoznam";
    }

    /**
     * Zobrazenie detailu poistenca.
     *
     * @param id ID poistenca, ktorého detail sa má zobraziť
     * @param model model pre Thymeleaf šablónu
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @return cesta k šablóne s detailom poistenca alebo presmerovanie pri nedostatočných oprávneniach
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, Authentication authentication) {
        try {
            // Načítanie poistenca a kontrola oprávnení
            PoistenecDTO poistenec = poistenecService.findById(id);
            if (!canAccess(authentication, poistenec.getEmail())) return "redirect:/poistenci";

            // Pridanie poistenca do modelu pre zobrazenie v šablóne
            model.addAttribute("poistenec", poistenec);
            return "pages/poistenec/detail";
        } catch (Exception exception) {
            // Presmerovanie pri chybe
            return "redirect:/poistenci";
        }
    }

    /**
     * Zobrazenie formulára pre vytvorenie nového poistenca.
     *
     * @param poistenec model atribút pre formulár
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @return cesta k šablóne s formulárom
     */
    @GetMapping("/novy")
    public String createForm(@ModelAttribute("poistenec") PoistenecDTO poistenec, Authentication authentication) {
        // Pre bežného používateľa predvyplníme email z prihlásenia
        if (authentication != null && !isAdmin(authentication)) {
            poistenec.setEmail(authentication.getName());
        }
        return "pages/poistenec/novy";
    }
    /**
     * Spracovanie formulára pre vytvorenie nového poistenca.
     *
     * @param poistenec dáta z formulára
     * @param bindingResult výsledky validácie
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @param redirectAttributes atribúty pre presmerovaciu požiadavku
     * @param model model pre Thymeleaf šablónu
     * @return presmerovanie na zoznam alebo späť na formulár pri chybe
     */
    @PostMapping
    public String create(@Valid @ModelAttribute("poistenec") PoistenecDTO poistenec,
                         BindingResult bindingResult,
                         Authentication authentication,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        // Kontrola validačných chýb
        if (bindingResult.hasErrors()) return "pages/poistenec/novy";

        // Kontrola oprávnení - bežný používateľ môže vytvoriť len seba
        if (!isAdmin(authentication) && !authentication.getName().equals(poistenec.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Môžete vytvoriť len vlastné poistenie.");
            return "redirect:/poistenci/novy";
        }

        try {
            // Vytvorenie poistenca
            poistenecService.create(poistenec);
            redirectAttributes.addFlashAttribute("success", "Poistenie vytvorené.");
            return "redirect:/poistenci";
        } catch (Exception exception) {
            // Spracovanie chyby
            model.addAttribute("error", exception.getMessage());
            return "pages/poistenec/novy";
        }
    }
    /**
     * Zobrazenie formulára pre úpravu poistenca.
     *
     * @param id ID poistenca, ktorý sa má upraviť
     * @param model model pre Thymeleaf šablónu
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @return cesta k šablóne s formulárom alebo presmerovanie pri nedostatočných oprávneniach
     */
    @GetMapping("/{id}/oprav")
    public String editForm(@PathVariable Long id, Model model, Authentication authentication) {
        try {
            // Načítanie poistenca a kontrola oprávnení
            PoistenecDTO poistenec = poistenecService.findById(id);
            if (!canAccess(authentication, poistenec.getEmail())) return "redirect:/poistenci";

            // Pridanie poistenca do modelu pre zobrazenie v šablóne
            model.addAttribute("poistenec", poistenec);
            return "pages/poistenec/oprav";
        } catch (Exception exception) {
            // Presmerovanie pri chybe
            return "redirect:/poistenci";
        }
    }
    /**
     * Spracovanie formulára pre úpravu poistenca.
     *
     * @param id ID poistenca, ktorý sa má upraviť
     * @param poistenec dáta z formulára
     * @param bindingResult výsledky validácie
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @param redirectAttributes atribúty pre presmerovaciu požiadavku
     * @param model model pre Thymeleaf šablónu
     * @return presmerovanie na zoznam alebo späť na formulár pri chybe
     */
    @PostMapping("/{id}/oprav")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("poistenec") PoistenecDTO poistenec,
                       BindingResult bindingResult,
                       Authentication authentication,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        // Kontrola validačných chýb
        if (bindingResult.hasErrors()) return "pages/poistenec/oprav";

        try {
            // Načítanie existujúceho poistenca a kontrola oprávnení
            PoistenecDTO existingPoistenec = poistenecService.findById(id);
            if (!canAccess(authentication, existingPoistenec.getEmail())) return "redirect:/poistenci";

            // Bežný používateľ nemôže zmeniť svoj email
            if (!isAdmin(authentication) && !existingPoistenec.getEmail().equals(poistenec.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "Nemôžete zmeniť email.");
                return "redirect:/poistenci/" + id + "/oprav";
            }

            // Aktualizácia poistenca
            poistenec.setId(id);
            poistenecService.update(poistenec);
            redirectAttributes.addFlashAttribute("success", "Poistenie aktualizované.");
            return "redirect:/poistenci";
        } catch (Exception exception) {
            // Spracovanie chyby
            model.addAttribute("error", exception.getMessage());
            return "pages/poistenec/oprav";
        }
    }

    /**
     * Vymazanie poistenca.
     *
     * @param id ID poistenca, ktorý sa má vymazať
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @param redirectAttributes atribúty pre presmerovaciu požiadavku
     * @return presmerovanie na zoznam alebo na odhlásenie, ak používateľ vymazal svoj profil
     */
    @GetMapping("/{id}/vymaz")
    public String delete(@PathVariable Long id, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            // Načítanie poistenca a kontrola oprávnení
            PoistenecDTO poistenec = poistenecService.findById(id);
            if (!canAccess(authentication, poistenec.getEmail())) return "redirect:/poistenci";

            // Vymazanie poistenca
            poistenecService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Poistenie odstránené.");

            // Ak používateľ vymazal sám seba a nie je admin, odhlásime ho
            if (authentication.getName().equals(poistenec.getEmail()) && !isAdmin(authentication)) {
                return "redirect:/account/logout";
            }
            return "redirect:/poistenci";
        } catch (Exception exception) {
            // Spracovanie chyby
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            return "redirect:/poistenci";
        }
    }

    /**
     * Spracovanie výnimky pre nenájdeného poistenca.
     *
     * @param redirectAttributes atribúty pre presmerovaciu požiadavku
     * @return presmerovanie na zoznam poistencov
     */
    @ExceptionHandler(PoistenecNotFoundException.class)
    public String handleError(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Poistenec nenájdený.");
        return "redirect:/poistenci";
    }

    /**
     * Kontroluje, či má používateľ administrátorské oprávnenia.
     *
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @return true ak má používateľ rolu ADMIN, inak false
     */
    private boolean isAdmin(Authentication authentication) {
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    /**
     * Kontroluje, či má používateľ oprávnenie pristupovať k záznamu.
     * Administrátor má prístup ku všetkým záznamom, bežný používateľ len k svojim.
     *
     * @param authentication autentifikačný objekt prihlaseného používateľa
     * @param email email poistenca v zázname
     * @return true ak má používateľ oprávnenie na prístup, inak false
     */
    private boolean canAccess(Authentication authentication, String email) {
        return isAdmin(authentication) || (authentication != null && authentication.getName().equals(email));
    }
}