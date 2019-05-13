package gui;

import java.util.Arrays;

public enum Grades {
    Geen_filter("Geen filter"),
    Zesde_Kyu("Zesde Kyu"),
    Vijfde_Kyu("Vijfde Kyu"),
    Vierde_Kyu("Vierde Kyu"),
    Derde_Kyu("Derde Kyu"),
    Tweede_Kyu("Tweede Kyu"),
    Eerste_Kyu("Eerste Kyu"),
    Eerste_Dan("Eerste Dan"),
    Tweede_Dan("Tweede Dan"),
    Derde_Dan("Derde Dan"),
    Vierde_Dan("Vierde Dan"),
    Vijfde_Dan("Vijfde Dan"),
    Zesde_Dan("Zesde Dan"),
    Zevende_Dan("Zevende Dan"),
    Achtste_Dan("Achtste Dan"),
    Negende_Dan("Negende Dan"),
    Tiende_Dan("Tiende Dan"),
    Elfde_Dan("Elfde Dan"),
    Twaalfde_Dan("Twaalfde Dan");

    private String displayName;

    Grades(String displayName) {
        this.displayName = displayName;
    }

    @Override public String toString() { return displayName; }
}
