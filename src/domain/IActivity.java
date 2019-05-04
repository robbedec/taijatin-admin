package domain;

public interface IActivity {
    public int getId();
    public String getName();
    public Integer getType();
    public int getMaxNumberOfParticipants();
    public int getNumberOfParticipants();
    public boolean getStatus();
}
