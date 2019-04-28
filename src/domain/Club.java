package domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import repository.*;

import javax.persistence.EntityNotFoundException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class Club {

    public UserDTO currentUser;
    private PropertyChangeSupport subject;

    private UserDao userRepo;
    private ActivityDao activityRepo;

    private List<User> userLijst;
    private ObservableList<UserDTO> userDTOLijst;
    private FilteredList<UserDTO> filteredList;
    private SortedList<UserDTO> sorderdList;

    private List<Activity> activityLijst;
    private ObservableList<ActivityDTO> activityDTOLijst;
    private FilteredList<ActivityDTO> filteredActivityList;
    private FilteredList<ActivityDTO> sortedActivityList;


    private final Comparator<UserDTO> byUsername = (p1,p2) -> p1.getUserName().compareToIgnoreCase(p2.getUserName());
    private final Comparator<UserDTO> byGrade = Comparator.comparing(UserDTO::getGrade);
    private final Comparator<UserDTO> sortOrder = byUsername.thenComparing(byGrade);

    public final String[] types = new String[]{ "Geen filter", "Member", "Teacher", "Admin" };

    public Club(){
        userRepo = new UserDaoJpa();
        activityRepo = new ActivityDaoJpa();
        userDTOLijst = FXCollections.observableArrayList();
        activityDTOLijst = FXCollections.observableArrayList();
        userRepo.getAll().forEach(user -> {
            System.out.print(user);
            UserDTO uDTO = user.toUserDTO(user);
            userDTOLijst.add(uDTO);
        });
        activityRepo.getAll().forEach(activity -> {
            System.out.println(activity);
            ActivityDTO aDTO = activity.toActivityDTO(activity);
            activityDTOLijst.add(aDTO);
        });
        filteredList = new FilteredList<>(userDTOLijst, p -> true);
        filteredActivityList = new FilteredList<>(activityDTOLijst, p -> true);
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

    public Collection<UserDTO> getFilteredMembers() {
        return sorderdList;
    }

    public void setCurrentUser(UserDTO user){
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
        try {
            User userToUpdate = userRepo.getByEmail(currentUser.getEmail());
            userRepo.insert(userToUpdate);
            userDTOLijst.add(currentUser);
        } catch (EntityNotFoundException ex) {
            UserDTO newUserDTO = currentUser;
            User newUser = newUserDTO.toUser(newUserDTO);
            userRepo.insert(newUser);
            userDTOLijst.add(newUserDTO);
        }


    }

    public void deleteUser(){
        User userToDelete = userRepo.getByEmail(currentUser.getEmail());
        userRepo.delete(userToDelete);
        userDTOLijst.remove(currentUser);
    }

}
