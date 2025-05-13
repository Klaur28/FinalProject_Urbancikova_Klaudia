package projekt.poistenie.controllers;

import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import projekt.poistenie.dtos.AccountDTO;
import projekt.poistenie.entities.Account;
import projekt.poistenie.exceptions.DuplicateEmailException;
import projekt.poistenie.exceptions.PasswordsDoNotEqualException;
import projekt.poistenie.service.AccountService;

/**
 * Controller pre správu používateľských účtov - prihlásenie a registráciu.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Zobrazí prihlasovaciu stránku
     *
     * @return cesta k šablóne login.html
     */
    @GetMapping("/login")
    public String renderLogin() {
        return "account/login";
    }

    /**
     * Zobrazí registračnú stránku s prázdnym formulárom.
     *
     * @param model model pre Thymeleaf šablónu
     * @return cesta k šablóne register.html
     */
    @GetMapping("/register")
    public String renderRegister(Model model) {
        model.addAttribute("accountDTO", new AccountDTO());
        return "account/register";
    }

    /**
     * Spracováva odoslanie registračného formulára.
     *
     * @param accountDTO dáta z formulára
     * @param result výsledky validácie
     * @param redirectAttributes atribúty pre redirect
     * @return návrat na formulár pri chybe
     */
    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute AccountDTO accountDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        // Ak sú chyby vo vstupných dátach, vrátime sa na formulár
        if (result.hasErrors()) {
            return "account/register";
        }

        try {
            // Delegujeme registráciu na service vrstvu
            accountService.register(accountDTO);

            // Pri úspešnej registrácii pridáme flash správu a presmerujeme na login
            redirectAttributes.addFlashAttribute("success", "Registrácia prebehla úspešne.");
            return "redirect:/account/login";

        } catch (PasswordsDoNotEqualException e) {
            // Ak sa heslá nezhodujú, pridáme chybu k obom políčkam
            result.rejectValue("password", "error", "Heslá sa nezhodujú.");
            result.rejectValue("passwordConfirmation", "error", "Heslá sa nezhodujú.");
            return "account/register";

        } catch (DuplicateEmailException e) {
            // Ak e-mail už existuje, pridáme chybu k políčku "email"
            result.rejectValue("email", "error", "Email je už používaný.");
            return "account/register";
        }
    }
}