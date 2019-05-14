package gui;

import java.util.Arrays;

public enum Status {
    Geen_Filter(0, "Status"),
    Inactief(1, "Inactief"),
    Actief(2, "Actief");

    public final int value;
    public String name;

    Status(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Integer valueOf(boolean condition) {
        int val;
        if(condition != true){
            val = 1;
        }
        else {
            val = 2;
        }
        return Arrays.stream(values())
                .filter(legNo -> legNo.value == val)
                .findFirst().get().value;
    }

    @Override
    public String toString() {
        return name;
    }
}
