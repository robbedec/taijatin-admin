package gui;

import java.util.Arrays;

public enum TypeOfActivity {
    Geen_Filter("Type", 0),
    Uitstap("Uitstap",1),
    Stage("Stage",2);

    private String displayName;
    private final int value;

    TypeOfActivity(String displayName, int value) {
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
