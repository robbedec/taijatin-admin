package repository;

import domain.TypeOfActivity;
import domain.User;

import java.util.Collection;

public class ActivityDTO {
    private String name;
    private TypeOfActivity type;
    private int numberOfParticipants;
    private boolean status;
    private Collection<User> usersById;

    public ActivityDTO() {
    }

    public ActivityDTO(String name, TypeOfActivity type, int numberOfParticipants, boolean status, Collection<User> usersById) {
        this.name = name;
        this.type = type;
        this.numberOfParticipants = numberOfParticipants;
        this.status = status;
        this.usersById = usersById;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeOfActivity getType() {
        return type;
    }

    public void setType(TypeOfActivity type) {
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
