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

    public void filterUsers(String userName, int index1, int index2){
        club.filterUsers(userName, index1, index2);
    }

    public void setCurrentUser(UserDTO userDto) {
        try {
            // User bestaat
            User u = club.getUserByEmail(userDto.getEmail());
            mergeUser(userDto.toUser(), u);
            club.setCurrentUser(u);
        } catch (CRuntimeException ex) {
            // Nieuwe user
            User u = userDto.toUser();
            club.setCurrentUser(u);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        club.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        club.removePropertyChangeListener(pcl);
    }

    public void updateUser() {
        System.out.println();
        club.updateUser();
    }

    public void deleteUser(){
        club.deleteUser();
    }

    public String[] getTypesOfUser(){
        return club.getTypesOfUser();
    }

    public Collection<String> getTeacherNames(){
        return club.getTeachers();
    }

    public User getTeacherByUserName(String userName){
        return club.getTeacherByUserName(userName);
    }

    public Collection<Activity> getFilteredActivities() {
        return club.getFilteredActivities();
    }

    public void setCurrentActivity(ActivityDTO activityDto){
        try {
            // Activity bestaat
            Activity a = club.getActivityByName(activityDto.getName());
            mergeActivity(activityDto.toActivity(), a);
            club.setCurrentActivity(a);
        } catch (CRuntimeException ex) {
            // Nieuwe activity
            Activity a = activityDto.toActivity();
            club.setCurrentActivity(a);
        }
    }

    public void filterActivities(String name, int index1, int index2){
        club.filterActivities(name, index1, index2);
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

    public void setActivityUserLists(ActivityDTO a){
        club.setActivityUserLists(a);
    }

    public void register(int index) {
        club.register(index);
    }

    public void undoRegister(int index) {
        club.undoRegister(index);
    }

    public int getTotalRegistered(){
        return club.getTotalRegistered();
    }

    public int getAmountOfUsers() {
        return club.getAmountOfUsers();
    }

    public void mergeUser(User dto, User original){
        original.setLastname(dto.getLastname());
        original.setLastname(dto.getLastname());
        original.setFirstname(dto.getFirstname());
        original.setBirthday(dto.getBirthday());
        original.setBornIn(dto.getBornIn());
        original.setRegistrationdate(dto.getRegistrationdate());
        original.setGrade(dto.getGrade());
        original.setGender(dto.getGender());
        original.setEmail(dto.getEmail());
        original.setMobilePhoneNumber(dto.getMobilePhoneNumber());
        original.setPhoneNumber(dto.getPhoneNumber());
        original.setNationalInsuranceNumber(dto.getNationalInsuranceNumber());
        original.setType(dto.getType());
        original.setAddressByAddressId(dto.getAddressByAddressId());
    }

    public void mergeActivity(Activity dto, Activity original) {
        original.setInfo(dto.getInfo());
        original.setName(dto.getName());
        original.setType(dto.getType());
        original.setStatus(dto.getStatus());
        original.setNumberOfParticipants(dto.getNumberOfParticipants());
        original.setMaxNumberOfParticipants(dto.getMaxNumberOfParticipants());
    }

    public void refrestNotRegisteredList(ActivityDTO aDto){
        club.refreshNotRegisteredList(aDto);
    }

    public void addNoMember(ActivityDTO activity, User u) {
        club.addNoMember(activity, u);
    }

    public boolean isFullActivity(){
        return club.isFullActivity();
    }

    public boolean isRegistered(String name) {
        return club.isRegistered(name);
    }
}
