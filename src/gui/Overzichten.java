package gui;

import java.util.Arrays;

public enum Overzichten {
    Activiteiten("Activiteiten", 0),
    Inschrijvingen("Inschrijvingen", 1),
    Clubkampioenschap("Clubkampioenschap", 2),
    Raadplegingen_lesmateriaal("Raadplegingen lesmateriaal", 3);

    private String displayName;
    private final int value;

    Overzichten(String displayName, int value) {
        this.displayName = displayName;
        this.value = value;
    }

    public static String valueOf(int value) {
        return Arrays.stream(values())
                .filter(legNo -> legNo.value == value)
                .findFirst().get().toString();
    }
    @Override public String toString() { return displayName; }
}
