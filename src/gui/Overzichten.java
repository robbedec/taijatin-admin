package gui;

import java.util.Arrays;

public enum Overzichten {
    Geen_filter(0),
    Activiteiten(1),
    Inschrijvingen(2),
    Clubkampioenschap(3),
    Raadplegingen_lesmateriaal(4);

    private final int value;

    Overzichten(int value) {
        this.value = value;
    }

    public static String valueOf(int value) {
        return Arrays.stream(values())
                .filter(legNo -> legNo.value == value)
                .findFirst().get().name();
    }
}
