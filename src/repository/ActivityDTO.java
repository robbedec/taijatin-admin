package repository;

import domain.Activity;
import domain.TypeOfActivity;
import domain.User;

import java.util.Collection;
import java.util.SplittableRandom;

public class ActivityDTO {
    private String name;
    private String info;
    private Integer type;
    private int numberOfParticipants;
    private boolean status;
    private Collection<User> usersById;

    public ActivityDTO() {
    }

    public ActivityDTO(String name, String info, Integer type, int numberOfParticipants, boolean status, Collection<User> usersById) {
        this.name = name;
        this.info = info;
        this.type = type;
        this.numberOfParticipants = numberOfParticipants;
        this.status = status;
        this.usersById = usersById;
    }

    public Activity toActivity(ActivityDTO activity){
        return new Activity(activity.name, activity.info, activity.type, activity.numberOfParticipants, activity.status, activity.usersById);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Collection<User> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<User> usersById) {
        this.usersById = usersById;
    }
}
