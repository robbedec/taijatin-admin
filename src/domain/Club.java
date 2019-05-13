package domain;

import gui.Grades;
import gui.TypeOfActivity;
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

    public User currentUser;
    public Activity currentActivity;
    private PropertyChangeSupport subjectUser;
    private PropertyChangeSupport subjectActivity;

    private UserDao userRepo;
    private ActivityDao activityRepo;

    private ObservableList<User> userList;
    private FilteredList<User> filteredList;
    private SortedList<User> sorderdList;

    private ObservableList<Activity> activityList;
    private FilteredList<Activity> filteredActivityList;
    private SortedList<Activity> sortedActivityList;

    //Observable Lists to be able to register an user to an activity
    private ObservableList<User> registeredUsersToActivityList;
    private ObservableList<User> notRegisteredUsersToActivityList;

    private final Comparator<User> byUsername = (p1,p2) -> p1.getUserName().compareToIgnoreCase(p2.getUserName());
    private final Comparator<User> byGrade = Comparator.comparing(User::getGrade);
    private final Comparator<User> sortOrder = byUsername.thenComparing(byGrade);

    private final Comparator<Activity> byActivityName = (p1,p2) -> p1.getName().compareToIgnoreCase(p2.getName());
    private final Comparator<Activity> byActivityType = Comparator.comparing(Activity::getType);
    private final Comparator<Activity> byStatus = Comparator.comparing(Activity::getStatus);
    private final Comparator<Activity> sortActivityOrder = byActivityType.thenComparing(byActivityName.thenComparing(byActivityType));

    private final String[] typesOfUser = new String[]{ "Geen filter", "Lid", "Lesgever", "Beheerder" };
    private final String[] typesOfActivity = new String[]{"Geen filter", "Uitstap", "Stage" };

    public Club(){
        userRepo = new UserDaoJpa();
        activityRepo = new ActivityDaoJpa();
        userList = FXCollections.observableArrayList();
        activityList = FXCollections.observableArrayList();
        userRepo.getAllButNoMembers().forEach(user -> {
            userList.add(user);
        });
        activityRepo.getAll().forEach(activity -> {
            activityList.add(activity);
        });
        filteredList = new FilteredList<>(userList, p -> true);
        filteredActivityList = new FilteredList<>(activityList, p -> true);
        sorderdList = new SortedList<>(filteredList, sortOrder);
        sortedActivityList = new SortedList<>(filteredActivityList, sortActivityOrder);
        subjectUser = new PropertyChangeSupport(this);
        subjectActivity = new PropertyChangeSupport(this);
        registeredUsersToActivityList = FXCollections.observableArrayList();
        currentUser = null;
        currentActivity = null;
    }

    public void filterUsers(String userName, int index1, int index2){
        if(index1 == 0 && index2 == 0) {
            filteredList.setPredicate(user -> user.getUserName().toLowerCase().startsWith(userName.toLowerCase()));
        } else if(index1 != 0 && index2 == 0){
            filteredList.setPredicate(user -> user.getUserName().toLowerCase().startsWith(userName.toLowerCase()) && user.getType().equals(typesOfUser[index1]));
        } /*else if(index2 != 0 && index1 == 0){
            filteredList.setPredicate(user -> user.getUserName().toLowerCase().startsWith(userName.toLowerCase()) && Grade.valueOf(user.getGrade()).equals(...));
        } else {
            filteredList.setPredicate(user -> user.getUserName().toLowerCase().startsWith(userName.toLowerCase()) && user.getType().equals(typesOfUser[index1]));
        }*/
    }

    public ObservableList<User> getFilteredMembers() {
        return sorderdList;
    }

    public void setCurrentUser(User user){
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
            // NIET VERANDERREN
            User u = userRepo.getByEmail(currentUser.getEmail());
            userRepo.insert(userRepo.update(this.currentUser));
            System.out.println(this.currentUser.getLastname());
        } catch (EntityNotFoundException ex) {
            userRepo.insert(currentUser);
            userList.add(currentUser);
        }
    }

    public void deleteUser(){
        User userToDelete = userRepo.getByEmail(currentUser.getEmail());
        userRepo.delete(userToDelete);
        userList.remove(currentUser);
    }

    public User getUserByEmail(String email) {
        try {
            User u = userRepo.getByEmail(email);
            return u;
        } catch (EntityNotFoundException ex) {
            throw new CRuntimeException("User with email: " + email + " not found!");
        }
    }

    public String[] getTypesOfUser(){
        return typesOfUser;
    }

    public void filterActivities(String activityName, int index){
        if(index == 0) {
            filteredActivityList.setPredicate(activity -> activity.getName().toLowerCase().startsWith(activityName.toLowerCase()));
        }
        else {
            filteredActivityList.setPredicate(activity -> activity.getName().toLowerCase().startsWith(activityName.toLowerCase()) && TypeOfActivity.valueOf(activity.getType()).equals(typesOfActivity[index]));
        }
    }

    public Collection<Activity> getFilteredActivities() {
        return sortedActivityList;
    }


    public void setCurrentActivity(Activity activity){
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
            activityRepo.insert(currentActivity);
            activityList.add(currentActivity);
        }
    }

    public void deleteActivity() {
        Activity activityToDelete = activityRepo.getByName(currentActivity.getName());
        activityRepo.delete(activityToDelete);
        activityList.remove(activityToDelete);
    }

    public Collection<User> getRegisteredUsersFromActivity(){
        return registeredUsersToActivityList;
    }

    public Collection<User> getNotRegisteredUsersFromActivity(){
        return notRegisteredUsersToActivityList;
    }

    public void setActivityUserLists(ActivityDTO aDto){
        Activity a = aDto.toActivity();
        if (a.getNotRegisteredUsersByUserId().size() == 0 && a.getRegisteredUsersByUserId().size() == 0) {
            this.notRegisteredUsersToActivityList = FXCollections.observableArrayList(userRepo.getAll());
        } else {
            this.notRegisteredUsersToActivityList = FXCollections.observableArrayList(a.getNotRegisteredUsersByUserId());
        }
        a.setNotRegisteredUsersByUserId(notRegisteredUsersToActivityList);
        System.out.println(notRegisteredUsersToActivityList);
        this.registeredUsersToActivityList = FXCollections.observableArrayList(a.getRegisteredUsersByUserId());
        a.setRegisteredUsersByUserId(registeredUsersToActivityList);
        if(a.getUsersById() == null){
            a.setUsersById(userRepo.getAll());
        }
    }

    public void register(int index) {
        if(index >= 0) {
            if(!isFullActivity()) {
                User user = notRegisteredUsersToActivityList.get(index);
                notRegisteredUsersToActivityList.remove(user);
                registeredUsersToActivityList.add(user);
                currentActivity.setNotRegisteredUsersByUserId(notRegisteredUsersToActivityList);
                currentActivity.setRegisteredUsersByUserId(registeredUsersToActivityList);
            }
            else {
                System.out.println("Maximum aantal deelnemers voor deze activiteit is bereikt. Indien er zich leden uitschrijven, kan er weer plaats vrijkomen...");
            }
        }
    }

    public boolean isFullActivity(){
        return currentActivity.getNumberOfParticipants() > currentActivity.getMaxNumberOfParticipants() - 1;
    }


    public void undoRegister(int index) {
        if(index >= 0){
            User user = registeredUsersToActivityList.get(index);
            registeredUsersToActivityList.remove(user);
            notRegisteredUsersToActivityList.add(user);
            currentActivity.setRegisteredUsersByUserId(registeredUsersToActivityList);
            currentActivity.setNotRegisteredUsersByUserId(notRegisteredUsersToActivityList);
        }
    }

    public int getTotalRegistered(){
        int total = currentActivity.getRegisteredUsersByUserId().size();
        System.out.println(total);
        currentActivity.setNumberOfParticipants(total);
        return total;
    }

    public Activity getActivityByName(String name) {
        try {
            Activity a = activityRepo.getByName(name);
            return a;
        } catch (EntityNotFoundException ex) {
            throw new CRuntimeException("User with email: " + name + " not found!");
        }
    }

    public int getAmountOfUsers() {
        return userList.size();
    }

    public void refreshNotRegisteredList(ActivityDTO aDto){
        Activity a = aDto.toActivity();
        this.notRegisteredUsersToActivityList = FXCollections.observableArrayList(userRepo.getAll());
        this.notRegisteredUsersToActivityList.removeAll(this.registeredUsersToActivityList);
        this.registeredUsersToActivityList = FXCollections.observableArrayList(userRepo.getAll());
        this.registeredUsersToActivityList.removeAll(this.notRegisteredUsersToActivityList);
        System.out.println(this.notRegisteredUsersToActivityList);
        a.setNotRegisteredUsersByUserId(this.notRegisteredUsersToActivityList);
        a.setRegisteredUsersByUserId(this.registeredUsersToActivityList);
        a.setUsersById(userRepo.getAll());
    }

    public void addNoMember(ActivityDTO activity, User u) {
        Activity a = activity.toActivity();
        this.registeredUsersToActivityList.add(u);
        a.setRegisteredUsersByUserId(this.registeredUsersToActivityList);

        userRepo.insert(u);
    }
}
