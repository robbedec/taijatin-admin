package domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import repository.AddressDao;
import repository.AddressDaoJpa;
import repository.GenericDao;
import repository.GenericDaoJpa;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.Comparator;

public class Club {

    public User currentUser;
    private PropertyChangeSupport subject;

    private GenericDao<User> userRepo;
    private GenericDao<Address> addressRepo;

    private ObservableList<User> userLijst;
    private FilteredList<User> filteredList;
    private SortedList<User> sorderdList;

    private final Comparator<User> byUsername = (p1,p2) -> p1.getUserName().compareToIgnoreCase(p2.getUserName());
    private final Comparator<User> byGrade = Comparator.comparing(User::getGrade);
    private final Comparator<User> sortOrder = byUsername.thenComparing(byGrade);

    public final String[] types = new String[]{ "Geen filter", "Member", "Teacher", "Admin" };

    public Club(){
        userRepo = new GenericDaoJpa<>(User.class);
        addressRepo = new GenericDaoJpa<>(Address.class);
        userLijst = FXCollections.observableList(userRepo.getAll());
        filteredList = new FilteredList<>(userLijst, p -> true);
        sorderdList = new SortedList<>(filteredList, sortOrder);
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
        return sorderdList;
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
        userLijst.add(currentUser);
    }

    public void deleteUser(){
        userRepo.delete(currentUser);
        userLijst.remove(currentUser);
    }

    public void addUser(User newUser){
        userRepo.insert(newUser);
        userLijst.add(newUser);
    }

    public Address getAddressById(int addressId) {
        return addressRepo.get(addressId);
    }
}
