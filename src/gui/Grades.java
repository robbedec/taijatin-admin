package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Grades {
    Geen_filter("Graad", 0),
    Zesde_Kyu("Zesde Kyu", 1),
    Vijfde_Kyu("Vijfde Kyu", 2),
    Vierde_Kyu("Vierde Kyu", 3),
    Derde_Kyu("Derde Kyu", 4),
    Tweede_Kyu("Tweede Kyu",5),
    Eerste_Kyu("Eerste Kyu", 6),
    Eerste_Dan("Eerste Dan", 7),
    Tweede_Dan("Tweede Dan",8),
    Derde_Dan("Derde Dan", 9),
    Vierde_Dan("Vierde Dan", 10),
    Vijfde_Dan("Vijfde Dan", 11),
    Zesde_Dan("Zesde Dan", 12),
    Zevende_Dan("Zevende Dan", 13),
    Achtste_Dan("Achtste Dan", 14),
    Negende_Dan("Negende Dan", 15),
    Tiende_Dan("Tiende Dan", 16),
    Elfde_Dan("Elfde Dan", 17),
    Twaalfde_Dan("Twaalfde Dan", 18);

    private String displayName;
    private final int value;

    Grades(String displayName, int value) {
        this.displayName = displayName;
        this.value = value;
    }

    @Override public String toString() { return displayName; }

    public static String valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(legNo -> legNo.value == value)
                .findFirst().get().name();
    }
}
