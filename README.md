**Aplikácia pre správu poistenia a poistencov**

Základné informácie

# 1. Kroky na spustenie aplikácie

    - Spustite lokálne MariaDB server na porte 3306
    - Vytvorte databázu s názvom `poistenie_db`
    - Skontrolujte nastavenia v application.properties:

   spring.datasource.url=jdbc:mariadb://localhost:3306/poistenie_db?serverTimezone=UTC&useSSL=false
   spring.datasource.username=root
   spring.datasource.password=
   server.port=8088

    - Spustite aplikáciu príkazom: ./mvnw spring-boot:ruN
    - Otvorte stránku: http://localhost:8088


# 2. Prístupové údaje

Aplikácia obsahuje prednastavené používateľské účty:

| Rola           | Email                | Heslo        | Popis                   |
|----------------|----------------------|--------------|-------------------------|
| ADMINISTRÁTOR  | urbancikova@admin.sk | urbancikova1 | Plný prístup           |
| POISTENEC      | adam@matej.sk        | adam123      | Prístup len k vlastným údajom |
| POISTENEC      | gabriel@astalos.sk   | gabriel123   | Prístup len k vlastným údajom |
| POISTENEC      | david@vesely.sk      | david123     | Prístup len k vlastným údajom |

# 3. Používateľské role
- Aplikácia podporuje dve používateľské role:

      - ADMINISTRÁTOR - má plný prístup ku všetkým záznamom v systéme
      - POISTENEC - má prístup len k vlastným záznamom a zmluvám

# 4. Funkcie aplikácie
    4.1. Správa poistencov
- Registrácia nového poistenca
      - Pre vytvorenie poistenca s rolou POISTENEC použite ľubovoľný email
      - Pre vytvorenie poistenca s rolou ADMINISTRÁTOR použite `@admin.sk`
- Zoznam všetkých poistencov (viditeľný len pre ADMINISTRÁTOR)
- Detail poistenca
- Úprava údajov poistenca
- Odstránenie poistenca


    4.2. Správa poistných zmlúv
- Vytvorenie novej zmluvy pre existujúceho poistenca
- Zobrazenie detailu zmluvy
- Úprava údajov zmluvy
- Odstránenie zmluvy


    4.3. Štatistiky
- Prehľad o počtoch poistencov a zmlúv
- Rozdelenie zmlúv podľa typu


# 5. Tipy na používanie
    5.1. Ako sa prihlásiť ako ADMINISTRÁTOR
- Navštívte stránku http://localhost:8088/account/login
- Zadajte email: urbancikova@admin.sk
- Zadajte heslo: urbancikova1
- Kliknite na "Prihlásiť sa"
    - Overte, že vidíte všetkých poistencov a zmluvy
    - Vyskúšajte upraviť údaje poistenca a zmluvy
    - Vytvorte nového poistenca a zmluvu
    - Odstráňte poistenca a zmluvu


    5.2. Ako sa prihlásiť ako POISTENEC
- Navštívte stránku http://localhost:8088/account/login
- Zadajte napr. email: adam@matej.sk
- Zadajte heslo: adam123
- Kliknite na "Prihlásiť sa"
    - Overte, že vidíte len vlastné záznamy
    - Vyskúšajte upraviť svoje údaje a zmluvy

    
    5.3. Ako sa zaregistrovať
- Navštívte stránku http://localhost:8088/account/register
- Vyplňte registračný formulár
  - Pre vytvorenie účtu s rolou ADMIN použite email končiaci na @admin.sk
  - Pre vytvorenie bežného účtu s rolou POISTENEC použite akýkoľvek iný email


    5.4. Ako pridať nového poistenca
- Prihláste sa
- Prejdite na sekciu "Poistenci"
- Kliknite na tlačidlo "Nový poistenec"
- Vyplňte formulár a potvrďte

    
    5.5. Ako vytvoriť novú poistnú zmluvu
- Prejdite na detail poistenca
- Kliknite na tlačidlo "Nová zmluva"
- Vyplňte údaje o zmluve a potvrďte


## 6. Riešenie problémov
    6.1. Ak sa aplikácia nespustí
- Skontrolujte, či beží MariaDB na porte 3306
- Overte, či existuje databáza `poistenie_db`
- Port 8088 nesmie byť obsadený inou aplikáciou

## 7. Kontakt
- V prípade otázok alebo problémov kontaktujte správcu aplikácie 
na adrese urbancikovaklaudia96@gmail.com alebo cez kontaktny formulár na stránke aplikácie


*Posledná aktualizácia: 13. máj 2025*