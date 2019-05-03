package domain;

import repository.ActivityDTO;

import javax.persistence.*;
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
    private int numberOfParticipants;
    private boolean status;

    public Activity(){}

    public Activity(String name, String info, Integer type, int numberOfParticipants, boolean status, Collection<User> usersById){
        this.name = name;
        this.info = info;
        this.type = type;
        this.numberOfParticipants = numberOfParticipants;
        this.status = status;
        this.usersById = usersById;
    }

    public ActivityDTO toActivityDTO(Activity activity){
        return new ActivityDTO(activity.getName(), activity.getInfo(), activity.getType(), activity.getNumberOfParticipants(), activity.getStatus(), activity.getUsersById());
    }

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
    @Column(name = "NumberOfParticipants")
    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
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
    @JoinColumn(name = "Id")
    private Collection<User> usersById;

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
}
