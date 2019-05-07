package repository;

import domain.Activity;
import domain.User;

import java.util.Collection;

public class ActivityDTO {
    private String name;
    private String info;
    private Integer type;
    private int maxNumberOfParticipants;
    private int numberOfParticipants;
    private boolean status;
    private Collection<User> usersById;
    private Collection<User> notRegisteredUsers;
    private Collection<User> registeredUsers;

    public ActivityDTO() {
    }

    public ActivityDTO(String name, String info, Integer type, int maxNumberOfParticipants, int numberOfParticipants, boolean status, Collection<User> usersById, Collection<User> notRegisteredUsers, Collection<User> registeredUsers) {
        this.name = name;
        this.info = info;
        this.type = type;
        this.maxNumberOfParticipants = maxNumberOfParticipants;
        this.numberOfParticipants = numberOfParticipants;
        this.status = status;
        this.usersById = usersById;
        this.notRegisteredUsers = notRegisteredUsers;
        this.registeredUsers = registeredUsers;
    }

    public Activity toActivity(ActivityDTO activity){
        return new Activity(activity.name, activity.info, activity.type, activity.numberOfParticipants, activity.numberOfParticipants, activity.status, activity.usersById, activity.notRegisteredUsers, activity.registeredUsers);
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

    public int getMaxNumberOfParticipants() {
        return maxNumberOfParticipants;
    }

    public void setMaxNumberOfParticipants(int maxNumberOfParticipants) {
        this.maxNumberOfParticipants = maxNumberOfParticipants;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public boolean getStatus() {
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
