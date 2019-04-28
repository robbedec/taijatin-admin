package domain;

public interface IActivity {
    public int getId();
    public String getName();
    public TypeOfActivity getType();
    public int getNumberOfParticipants();
    public boolean getStatus();
}
