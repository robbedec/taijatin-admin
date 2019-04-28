package domain;

public enum TypeOfActivity {
    UITSTAP(0), STAGE(1);

    public final Integer index;

    TypeOfActivity(Integer index) {
        this.index = index;
    }
}
