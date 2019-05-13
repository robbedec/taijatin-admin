package domain;

import repository.ActivityDTO;

import javax.persistence.*;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Activities")
@NamedQueries({
        @NamedQuery(name = "Activities.findAll", query = "SELECT a FROM Activity a"),
        @NamedQuery(name = "Users.findByName", query = "SELECT a FROM Activity a WHERE a.name = :name")
})
public class Activity implements IActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String info;
    private Integer type;
    private int maxNumberOfParticipants;
    private int numberOfParticipants;
    private boolean status;

    public Activity(){}

    public Activity(String name, String info, Integer type, int maxNumberOfParticipants, int numberOfParticipants, boolean status, Collection<User> usersById, Collection<User> notRegisteredUsersByUserId, Collection<User> registeredUsersByUserId){
        setName(name);
        setInfo(info);
        setType(type);
        setMaxNumberOfParticipants(maxNumberOfParticipants);
        setNumberOfParticipants(numberOfParticipants);
        setStatus(status);
        setUsersById(usersById);
        setNotRegisteredUsersByUserId(notRegisteredUsersByUserId);
        setRegisteredUsersByUserId(registeredUsersByUserId);
    }

    public ActivityDTO toActivityDTO(Activity activity){
        return new ActivityDTO(activity.id, activity.getName(), activity.getInfo(), activity.getType(), activity.getMaxNumberOfParticipants(), activity.getNumberOfParticipants(), activity.getStatus(), activity.getUsersById(), activity.getNotRegisteredUsersByUserId(), activity.getRegisteredUsersByUserId());
    }

    @Transient
    private PropertyChangeSupport subject = new PropertyChangeSupport(this);

    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(empty(name)){
            throw new CRuntimeException("Naam van activiteit kan niet leeg zijn!");
        }
        subject.firePropertyChange("name", this.name, name);
        this.name = name;
    }

    @Basic
    @Column(name = "Info")
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Basic
    @Column(name = "Type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "MaxNumberOfParticipants")
    public int getMaxNumberOfParticipants() {
        return maxNumberOfParticipants;
    }

    public void setMaxNumberOfParticipants(int maxNumberOfParticipants) {
        this.maxNumberOfParticipants = maxNumberOfParticipants;
    }

    @Column(name = "MaxNumberOfParticipants")
    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        if(registeredUsersByUserId == null){
            this.numberOfParticipants = 0;
        }
        this.numberOfParticipants = numberOfParticipants;
    }

    @Basic
    @Column(name = "Status")
    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "Activity_User",
        joinColumns = {@JoinColumn(name = "fk_activity")},
        inverseJoinColumns = {@JoinColumn(name = "fk_user") })
    private Collection<User> usersById;

    @ManyToMany
    @JoinTable(name = "Activity_User",
            joinColumns = {@JoinColumn(name = "fk_registeredToActivity")},
            inverseJoinColumns = {@JoinColumn(name = "fk_registeredUser") })
    public Collection<User> registeredUsersByUserId;

    @ManyToMany
    @JoinTable(name = "Activity_User",
            joinColumns = {@JoinColumn(name = "fk_notRegisteredToActivity")},
            inverseJoinColumns = {@JoinColumn(name = "fk_notRegisteredUser") })
    private Collection<User> notRegisteredUsersByUserId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return id == activity.id &&
                numberOfParticipants == activity.numberOfParticipants &&
                status == activity.status &&
                Objects.equals(name, activity.name) &&
                type == activity.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, numberOfParticipants, status);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Collection<User> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<User> usersById) {
        this.usersById = usersById;
    }

    public Collection<User> getRegisteredUsersByUserId() {
        return registeredUsersByUserId == null ? new ArrayList<>() : registeredUsersByUserId;
    }

    public void setRegisteredUsersByUserId(Collection<User> registeredUsersByUserId) {
         this.registeredUsersByUserId = registeredUsersByUserId;
    }

    public Collection<User> getNotRegisteredUsersByUserId() {
        return notRegisteredUsersByUserId  == null ? new ArrayList<>() : notRegisteredUsersByUserId;
    }

    public void setNotRegisteredUsersByUserId(Collection<User> notRegisteredUsersByUserId) {
        this.notRegisteredUsersByUserId = notRegisteredUsersByUserId;
    }

    public void addRegisteredUser(User user){
        this.getRegisteredUsersByUserId().add(user);
        this.getNotRegisteredUsersByUserId().remove(user);
    }

    public void deleteRegisteredUser(User user){
        this.getRegisteredUsersByUserId().add(user);
        this.getNotRegisteredUsersByUserId().remove(user);
    }

    private boolean empty(String string) {
        if (string.trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
