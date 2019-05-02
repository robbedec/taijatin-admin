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
    public ActivityDTO currentActivity;
    private PropertyChangeSupport subjectUser;
    private PropertyChangeSupport subjectActivity;

    private UserDao userRepo;
    private ActivityDao activityRepo;

    private List<User> userLijst;
    private ObservableList<UserDTO> userDTOLijst;
    private FilteredList<UserDTO> filteredList;
    private SortedList<UserDTO> sorderdList;

    private List<Activity> activityLijst;
    private ObservableList<ActivityDTO> activityDTOLijst;
    private FilteredList<ActivityDTO> filteredActivityList;
    private SortedList<ActivityDTO> sortedActivityList;

    private final Comparator<UserDTO> byUsername = (p1,p2) -> p1.getUserName().compareToIgnoreCase(p2.getUserName());
    private final Comparator<UserDTO> byGrade = Comparator.comparing(UserDTO::getGrade);
    private final Comparator<UserDTO> sortOrder = byUsername.thenComparing(byGrade);

    private final Comparator<ActivityDTO> byActivityName = (p1,p2) -> p1.getName().compareToIgnoreCase(p2.getName());
    private final Comparator<ActivityDTO> byActivityType = Comparator.comparing(ActivityDTO::getType);
    private final Comparator<ActivityDTO> sortActivityOrder = byActivityName.thenComparing(byActivityType);

    private final String[] typesOfUser = new String[]{ "Geen filter", "Member", "Teacher", "Admin" };
    private final String[] typesOfActivity = new String[]{"Geen filter", "Uitstap", "Stage" };

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
        sortedActivityList = new SortedList<>(filteredActivityList, sortActivityOrder);
        subjectUser = new PropertyChangeSupport(this);
        subjectActivity = new PropertyChangeSupport(this);
        currentUser = null;
    }

    public void filterUsers(String userName, int index){
        if(index == 0) {
            filteredList.setPredicate(user -> user.getUserName().toLowerCase().startsWith(userName.toLowerCase()));
        } else {
            filteredList.setPredicate(user -> user.getUserName().toLowerCase().startsWith(userName.toLowerCase()) && user.getType().equals(typesOfUser[index]));
        }
    }

    public Collection<UserDTO> getFilteredMembers() {
        return sorderdList;
    }

    public void setCurrentUser(UserDTO user){
        subjectUser.firePropertyChange("user", this.currentUser, user);
        this.currentUser = user;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        subjectUser.addPropertyChangeListener(pcl);
        pcl.propertyChange(new PropertyChangeEvent(pcl, "user", null, this.currentUser));
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        subjectUser.removePropertyChangeListener(pcl);
    }

    public void updateUser() {
        try {
            User userToUpdate = userRepo.getByEmail(currentUser.getEmail());
            userRepo.insert(userToUpdate);
            //userDTOLijst.add(currentUser);
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

    public String[] getTypesOfUser(){
        return typesOfUser;
    }

    public void filterActivities(String activityName, int index){
        if(index == 0) {
            filteredActivityList.setPredicate(activity -> activity.getName().toLowerCase().startsWith(activityName.toLowerCase()));
        }
        else {
            filteredActivityList.setPredicate(activity -> activity.getName().toLowerCase().startsWith(activityName.toLowerCase()) &&  activity.getType().equals(typesOfActivity[index]));
        }
    }

    public Collection<ActivityDTO> getFilteredActivities() {
        return sortedActivityList;
    }


    public void setCurrentActivity(ActivityDTO activity){
        subjectActivity.firePropertyChange("activity", this.currentActivity, activity);
        this.currentActivity = activity;
    }

    public void addActivityPropertyChangeListener(PropertyChangeListener pcl) {
        subjectActivity.addPropertyChangeListener(pcl);
        pcl.propertyChange(new PropertyChangeEvent(pcl, "activity", null, this.currentActivity));
    }


    public void removeActivityPropertyChangeListener(PropertyChangeListener pcl) {
        subjectUser.removePropertyChangeListener(pcl);
    }

    public void updateActivity(){
        try {
            Activity activityToUpdate = activityRepo.getByName(currentActivity.getName());
            activityRepo.insert(activityToUpdate);
        } catch (EntityNotFoundException ex) {
            ActivityDTO newActivityDTO = currentActivity;
            Activity newActivity = newActivityDTO.toActivity(newActivityDTO);
            activityRepo.insert(newActivity);
            activityDTOLijst.add(newActivityDTO);
        }
    }

    public void deleteActivity() {
        Activity activityToDelete = activityRepo.getByName(currentActivity.getName());
        activityRepo.delete(activityToDelete);
        activityDTOLijst.remove(activityToDelete);
    }

    public String[] getTypesOfActivity(){
        return typesOfActivity;
    }

}
