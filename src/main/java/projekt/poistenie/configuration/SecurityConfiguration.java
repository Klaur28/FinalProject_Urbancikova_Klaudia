package projekt.poistenie.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration                      // Označuje triedu ako Spring konfiguracnú
@EnableWebSecurity                  // Zapína webovú bezpečnosť v aplikácii
@EnableMethodSecurity(             // Povolenie anotácií @Secured a @RolesAllowed
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfiguration {

    /**
     * Definuje poradie a pravidlá bezpečnostných filtrov (SecurityFilterChain).
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        // Verejné cesty bez prihlásenia
                        .requestMatchers(
                                "/",                     // domovská stránka
                                "/account/login",        // prihlasovanie
                                "/account/register",     // registrácia
                                "/account/process-login",// spracovanie prihlasovacích údajov
                                "/css/**",               // statické CSS súbory
                                "/js/**",                // statické JS súbory
                                "/images/**",            // obrázky
                                "/error"                 // chybová stránka
                        ).permitAll()
                        // Ostatné požiadavky vyžadujú prihlásenie
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // Vlastná prihlasovacia stránka a spracovanie
                        .loginPage("/account/login")
                        .loginProcessingUrl("/account/process-login")
                        // Po úspešnom prihlásení presmeruj na /poistenci
                        .defaultSuccessUrl("/poistenci", true)
                        // Parameter pre používateľské meno (e-mail)
                        .usernameParameter("email")
                        .permitAll()
                )
                .logout(logout -> logout
                        // Vlastné URL pre odhlásenie a úspešné odhlásenie
                        .logoutUrl("/account/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .build();
    }

    /**
     * Bean a používa na hashovanie hesiel pomocou BCrypt algoritmu.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
