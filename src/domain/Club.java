package domain;

import gui.Status;
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
    private FormulaDayDao formulaDayRepo;

    private ObservableList<User> userList;
    private FilteredList<User> filteredList;
    private SortedList<User> sorderdList;

    private ObservableList<Activity> activityList;
    private FilteredList<Activity> filteredActivityList;
    private SortedList<Activity> sortedActivityList;

    //Observable Lists to be able to register an user to an activity
    private ObservableList<User> registeredUsersToActivityList;
    private ObservableList<User> notRegisteredUsersToActivityList;
    private FilteredList<User> filteredNotRegisteredUsers;
    private SortedList<User> sortedNotRegisteredUserList;

    private final Comparator<User> byUsername = (p1,p2) -> p1.getUserName().compareToIgnoreCase(p2.getUserName());
    private final Comparator<User> byGrade = Comparator.comparing(User::getGrade);
    private final Comparator<User> sortOrder = byUsername.thenComparing(byGrade);
    private final Comparator<User> byIsNoMember = Comparator.comparing(User::getIsNoMember);
    private final Comparator<User> sortOrderInActivity = byUsername.thenComparing(byIsNoMember);

    private final Comparator<Activity> byActivityName = (p1,p2) -> p1.getName().compareToIgnoreCase(p2.getName());
    private final Comparator<Activity> byActivityType = Comparator.comparing(Activity::getType);
    private final Comparator<Activity> byStatus = Comparator.comparing(Activity::getStatus);
    private final Comparator<Activity> sortActivityOrder = byActivityType.thenComparing(byActivityName.thenComparing(byActivityType));

    private final String[] typesOfUser = new String[]{ "Geen filter", "Lid", "Lesgever", "Beheerder" };
    private final String[] typesOfActivity = new String[]{"Geen filter", "Uitstap", "Stage" };

    public Club(){
        userRepo = new UserDaoJpa();
        activityRepo = new ActivityDaoJpa();
        formulaDayRepo = new FormulaDayDayDaoJpa();
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
        } else if(index2 != 0 && index1 == 0){
            filteredList.setPredicate(user -> user.getUserName().toLowerCase().startsWith(userName.toLowerCase()) && user.getGrade().equals(index2 - 1));
        } else {
            filteredList.setPredicate(user -> user.getUserName().toLowerCase().startsWith(userName.toLowerCase()) && user.getType().equals(typesOfUser[index1]) && user.getGrade().equals(index2 - 1));
        }
    }

    public ObservableList<User> getFilteredMembers() {
        return sorderdList;
    }

    public Collection<String> getTeachers(){
        Collection<String> teacherNames = new ArrayList<>();
        userRepo.getAllTeachers().forEach(t -> teacherNames.add(t.getUserName()));
        return teacherNames;
    }

    public User getTeacherByUserName(String userName){
        return userRepo.getByUserName(userName);
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

    public void filterActivities(String activityName, int index1, int index2){
        if(index1 == 0 && index2 == 0) {
            filteredActivityList.setPredicate(activity -> activity.getName().toLowerCase().startsWith(activityName.toLowerCase()));
        }
        else if(index1 != 0 && index2 == 0){
            filteredActivityList.setPredicate(activity -> activity.getName().toLowerCase().startsWith(activityName.toLowerCase()) && TypeOfActivity.valueOf(activity.getType()).equals(typesOfActivity[index1]));
        }
        else if(index2 != 0 && index1 == 0){
            filteredActivityList.setPredicate(activity -> activity.getName().toLowerCase().startsWith(activityName.toLowerCase()) && Status.valueOf(activity.getStatus()).equals(index2));
        }
        else {
            filteredActivityList.setPredicate(activity -> activity.getName().toLowerCase().startsWith(activityName.toLowerCase()) && TypeOfActivity.valueOf(activity.getType()).equals(typesOfActivity[index1]) && Status.valueOf(activity.getStatus()).equals(index2));
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
            this.notRegisteredUsersToActivityList = FXCollections.observableArrayList(userRepo.getAllButNoMembers());
            this.notRegisteredUsersToActivityList.removeAll(userRepo.getAllAdmins());
        } else {
            this.notRegisteredUsersToActivityList = FXCollections.observableArrayList(a.getNotRegisteredUsersByUserId());
            this.notRegisteredUsersToActivityList.removeAll(userRepo.getAllAdmins());
        }
        a.setNotRegisteredUsersByUserId(notRegisteredUsersToActivityList);
        System.out.println(notRegisteredUsersToActivityList);
        this.registeredUsersToActivityList = FXCollections.observableArrayList(a.getRegisteredUsersByUserId());
        a.setRegisteredUsersByUserId(registeredUsersToActivityList);
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
        this.notRegisteredUsersToActivityList = FXCollections.observableArrayList(userRepo.getAllButNoMembers());
        this.notRegisteredUsersToActivityList.removeAll(this.registeredUsersToActivityList);
        this.notRegisteredUsersToActivityList.removeAll(userRepo.getAllAdmins());
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

    public boolean isRegistered(String name) {
        List<String> namesInRegistered = new ArrayList<>();
        registeredUsersToActivityList.forEach(user -> namesInRegistered.add(user.getUserName().toLowerCase()));
        System.out.println(namesInRegistered.contains(name.toLowerCase()));
        return namesInRegistered.contains(name.toLowerCase());
    }

    public void addFormulaDaysToFormula(String formulaName){
        Collection<FormulaDay> formulaDays = new ArrayList<>();
        switch (formulaName){
            case "DI_DO":
                formulaDays.add(getFormulaDayOne(2));
                formulaDays.add(getFormulaDayTwo(4));
            case "DI_ZA":
                formulaDays.add(getFormulaDayOne(2));
                formulaDays.add(getFormulaDayTwo(6));
            case "WO_ZA":
                formulaDays.add(getFormulaDayOne(3));
                formulaDays.add(getFormulaDayTwo(6));
            case "WO":
                formulaDays.add(getFormulaDayOne(3));
            case "ZA":
                formulaDays.add(getFormulaDayOne(6));
            case "ZO":
                formulaDays.add(getFormulaDayOne(7));
            case "Geen":
            default:
        }
        currentUser.getFormulasByFormulaId().setFormulaFormulaDaysByFormulaId(formulaDays);
    }

    private FormulaDay getFormulaDayOne(int day1){
        List<FormulaDay> formulaDays = new ArrayList<>(currentUser.getFormulasByFormulaId().getFormulaDaysByFormulaId());
        FormulaDay formulaDay1 = formulaDays.get(0);
        if(day1 != formulaDay1.getDay()){
            formulaDay1 = formulaDayRepo.getByDay(day1);
        }
        return formulaDay1;
    }

    private FormulaDay getFormulaDayTwo(int day2){
        List<FormulaDay> formulaDays = new ArrayList<>(currentUser.getFormulasByFormulaId().getFormulaDaysByFormulaId());
        FormulaDay formulaDay2 = formulaDays.get(1);
        if(day2 != formulaDay2.getDay() && day2 != 0){
            formulaDay2 = formulaDayRepo.getByDay(day2);
        }
        return formulaDay2;
    }

}
