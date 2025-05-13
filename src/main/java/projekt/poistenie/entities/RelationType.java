package projekt.poistenie.entities;

/**
 * Enumerácia reprezentujúca typy vzťahov poistenca k poistnej zmluve.
 * Používa sa na definovanie rôznych rolí, ktoré môže mať poistenec vo vzťahu
 * k poistnej zmluve (platiteľ poistného alebo poistená osoba).
 */
public enum RelationType {
    /**
     * Označuje osobu, ktorá je platiteľom poistného.
     * Táto osoba platí poistné, ale nemusí byť nutne aj poistenou osobou.
     */
    PAYER("Platiteľ"),

    /**
     * Označuje osobu, ktorá je poistená.
     * Na túto osobu sa vzťahuje poistné krytie, ale nemusí byť nutne aj platiteľom.
     */
    COVERED("Poistený");

    /**
     * Popisný text pre daný typ vzťahu, ktorý sa zobrazuje v používateľskom rozhraní.
     */
    private final String label;

    /**
     * Konštruktor pre vytvorenie novej hodnoty enumeration s príslušným popisom.
     *
     * @param label popisný text pre daný typ vzťahu
     */
    RelationType(String label) {
        this.label = label;
    }

    /**
     * Vráti popisný text pre daný typ vzťahu.
     * Tento text je určený na zobrazenie v používateľskom rozhraní.
     *
     * @return popisný text pre daný typ vzťahu
     */
    public String getLabel() {
        return label;
    }
}