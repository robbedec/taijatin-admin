package domain;

import repository.ActivityDTO;

public interface IActivity {
    public int getId();
    public String getName();
    public String getInfo();
    public Integer getType();
    public int getMaxNumberOfParticipants();
    public int getNumberOfParticipants();
    public boolean getStatus();

    ActivityDTO toActivityDTO(Activity activity);
}
