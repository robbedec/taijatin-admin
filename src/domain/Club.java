package domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import repository.GenericDao;
import repository.GenericDaoJpa;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLOutput;
import java.util.Collection;

public class Club {

    public User currentUser;
    private PropertyChangeSupport subject;

    private GenericDao<User> userRepo;

    private ObservableList<User> userLijst;
    private FilteredList<User> filteredList;

    public final String[] types = new String[]{ "Geen filter", "Member", "Teacher", "Admin" };

    public Club(){
        userRepo = new GenericDaoJpa<>(User.class);
        userLijst = FXCollections.observableList(userRepo.getAll());
        filteredList = new FilteredList<>(userLijst, p -> true);
        subject = new PropertyChangeSupport(this);
        currentUser = null;
    }

    public void filterUsers(String userName, int index){
        if(index == 0) {
            filteredList.setPredicate(user -> user.getUserName().toLowerCase().startsWith(userName.toLowerCase()));
        } else {
            filteredList.setPredicate(user -> user.getUserName().toLowerCase().startsWith(userName.toLowerCase()) && user.getType().equals(types[index]));
        }
    }

    public Collection<User> getFilteredMembers() {
        return filteredList;
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

    public void updateUser() {
        userRepo.insert(currentUser);
    }

    public void deleteUser(){
        userRepo.delete(currentUser);
        userLijst.remove(currentUser);
    }

    public void addUser(User newUser){
        System.out.println(currentUser.getFirstname());
        System.out.println(currentUser.getLastname());
        System.out.println(currentUser.getEmail());
        userRepo.insert(newUser);
        userLijst.add(newUser);

    }

}
