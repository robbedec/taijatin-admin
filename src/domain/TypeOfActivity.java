package domain;

import java.util.Arrays;

public enum TypeOfActivity {
    UITSTAP(0),
    STAGE(1);

    private final int value;

    TypeOfActivity(int value) {
        this.value = value;
    }

    public static String valueOf(int value) {
        return Arrays.stream(values())
                .filter(legNo -> legNo.value == value)
                .findFirst().get().name();
    }
}
