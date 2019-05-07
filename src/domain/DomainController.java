package domain;


import javafx.collections.ObservableList;
import repository.ActivityDTO;
import repository.UserDTO;

import java.beans.PropertyChangeListener;
import java.util.Collection;

public class DomainController {

    private Club club;

    public DomainController(){      // DC is een façade die dingen doorgeeft aan de presentatielaag => Mag enkel dingen doorgeven, niet zelf methodes uitwerken.
        club = new Club();
    }

    public ObservableList<User> getFilteredMembers() {
        return club.getFilteredMembers();
    }

    public void filterUsers(String userName, int index){
        club.filterUsers(userName, index);
    }

    public void setCurrentUser(User user){
        club.setCurrentUser(user);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        club.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        club.removePropertyChangeListener(pcl);
    }

    public void updateUser() {
        club.updateUser();
    }

    public void deleteUser(){
        club.deleteUser();
    }

    public String[] getTypesOfUser(){
        return club.getTypesOfUser();
    }

    public Collection<Activity> getFilteredActivities() {
        return club.getFilteredActivities();
    }

    public void setCurrentActivity(Activity activity){
        club.setCurrentActivity(activity);
    }

    public void filterActivities(String name, int index){
        club.filterActivities(name, index);
    }

    public void addActivityPropertyChangeListener(PropertyChangeListener pcl) {
        club.addActivityPropertyChangeListener(pcl);
    }

    public void removeActivityPropertyChangeListener(PropertyChangeListener pcl) {
        club.removeActivityPropertyChangeListener(pcl);
    }

    public void updateActivity() {
        club.updateActivity();
    }

    public void deleteActivity(){
        club.deleteActivity();
    }

    public Collection<User> getRegisteredUsersFromActivity(){
        return club.getRegisteredUsersFromActivity();
    }

    public Collection<User> getNotRegisteredUsersFromActivity(){
        return club.getNotRegisteredUsersFromActivity();
    }


}
