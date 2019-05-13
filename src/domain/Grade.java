package domain;

import java.util.Arrays;

public enum Grade {
    Zesde_Kyu(0),
    Vijfde_Kyu(1),
    Vierde_Kyu(2),
    Derde_Kyu(3),
    Tweede_Kyu(4),
    Eerste_Kyu(5),
    Eerste_Dan(6),
    Tweede_Dan(7),
    Derde_Dan(8),
    Vierde_Dan(9),
    Vijfde_Dan(10),
    Zesde_Dan(11),
    Zevende_Dan(12),
    Achtste_Dan(13),
    Negende_Dan(14),
    Tiende_Dan(15),
    Elfde_Dan(16),
    Twaalfde_Dan(17);

    private final int value;

    Grade(int value) {
        this.value = value;
    }

    public static String valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(legNo -> legNo.value == value)
                .findFirst().get().name();
    }
}
