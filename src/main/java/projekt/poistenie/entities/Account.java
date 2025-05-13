package projekt.poistenie.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Entita Account predstavuje používateľský účet v DB (tabuľka "accounts")
 * a implementuje UserDetails pre Spring Security.
 */
@Entity
@Table(name = "accounts")
public class Account implements UserDetails {

    /** Primárny kľúč, auto-generovaný pri vložení záznamu. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** E-mail používateľa, musí byť jedinečný a nesmie byť null. */
    @Column(unique = true, nullable = false)
    private String email;

    /** Zahashované heslo používateľa, nesmie byť null. */
    @Column(nullable = false)
    private String password;

    /** Dátum a čas vytvorenia účtu, nastavené automaticky pred prvým zápisom. */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /** Rola používateľa (USER alebo ADMIN), uložená ako text. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /** Možné roly účtu v systéme. */
    public enum Role {
        USER, ADMIN
    }

    /**
     * Pred uložením nového účtu sa automaticky nastaví createdAt
     * na aktuálny čas.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    /**
     * Vráti zoznam oprávnení (authorities) pre Spring Security.
     * Konvertuje enum role na tvar "ROLE_<ROLE_NAME>".
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /** Používa sa ako používateľské meno (username) v Spring Security. */
    @Override
    public String getUsername() {
        return email;
    }

    // Nižšie metódy vracajú true, aby účet neexpiroval, nebol zablokovaný,
    // kredenciály neexpirovali a účet bol povolený.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    // ----- Gettery a settery pre vlastné polia -----

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return e-mail používateľa (spravidla slúži ako login).
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email nastaví e-mail (login) používateľa.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return zahashované heslo pre autentifikáciu.
     */
    @Override
    public String getPassword() {
        return password;
    }
    /**
     * @param password nastaví zahashované heslo.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return čas vytvorenia účtu.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    /**
     * @param createdAt nastaví dátum/čas vytvorenia (zvyčajne @PrePersist).
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return rola používateľa (USER alebo ADMIN).
     */
    public Role getRole() {
        return role;
    }
    /**
     * @param role nastaví rolu používateľa.
     */
    public void setRole(Role role) {
        this.role = role;
    }
}
