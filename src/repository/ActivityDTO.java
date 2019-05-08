package repository;

import domain.Activity;
import domain.User;

import java.util.Collection;

public class ActivityDTO {
    private int id;
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

    public ActivityDTO(int id, String name, String info, Integer type, int maxNumberOfParticipants, int numberOfParticipants, boolean status, Collection<User> usersById, Collection<User> notRegisteredUsers, Collection<User> registeredUsers) {
        this.id = id;
        setName(name);
        setInfo(info);
        setType(type);
        setMaxNumberOfParticipants(maxNumberOfParticipants);
        setNumberOfParticipants(numberOfParticipants);
        setStatus(status);
        setUsersById(usersById);
        setNotRegisteredUsers(notRegisteredUsers);
        setRegisteredUsers(registeredUsers);
    }

    public Activity toActivity(){
        return new Activity(this.getName(), this.getInfo(), this.getType(), this.getMaxNumberOfParticipants(), this.getNumberOfParticipants(), this.getStatus(), this.getUsersById(), this.getNotRegisteredUsers(), this.getRegisteredUsers());
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

    public Collection<User> getNotRegisteredUsers() {
        return notRegisteredUsers;
    }

    public void setNotRegisteredUsers(Collection<User> notRegisteredUsers) {
        this.notRegisteredUsers = notRegisteredUsers;
    }

    public Collection<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(Collection<User> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }
}
