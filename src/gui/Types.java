package gui;

import java.util.Arrays;

public enum Types {
    Geen_filter("Type"),
    Lid("Lid"),
    Leraar("Lesgever"),
    Beheerder("Beheerder");

    private String displayName;

    Types(String displayName) { this.displayName = displayName; }

    @Override public String toString() { return displayName; }
}
