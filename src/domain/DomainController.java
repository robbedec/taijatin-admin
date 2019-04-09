package domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import repository.GenericDao;
import repository.GenericDaoJpa;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.stream.Collectors;

public class DomainController {

    public User currentUser;
    private PropertyChangeSupport subject;

    private GenericDao<User> userRepo;

    private ObservableList<User> userLijst;
    private FilteredList<User> filteredList;

    public DomainController(){
        userRepo = new GenericDaoJpa<>(User.class);
        userLijst = FXCollections.observableList(userRepo.getAll());
        filteredList = new FilteredList<>(userLijst, p -> true);
        subject = new PropertyChangeSupport(this);
        currentUser = null;
    }

    public Collection<User> getFilteredMembers() {
        return filteredList;
    }

    public void filter(String userName){
        filteredList.setPredicate(user -> user.getUserName().toLowerCase().startsWith(userName.toLowerCase()));
    }

    public void setCurrentUser(User user){
        subject.firePropertyChange("user", this.currentUser, user);
        this.currentUser = user;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        subject.addPropertyChangeListener(pcl);
        pcl.propertyChange(new PropertyChangeEvent(pcl, "user", null, this.currentUser));
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        subject.removePropertyChangeListener(pcl);
    }

}
