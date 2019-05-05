package gui;

import java.util.Arrays;

public enum Types {
    Geen_filter("Geen filter"),
    Lid("Lid"),
    Leraar("Leraar"),
    Beheerder("Beheerder");

    private String displayName;

    Types(String displayName) { this.displayName = displayName; }

    @Override public String toString() { return displayName; }
}
