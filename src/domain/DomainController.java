package domain;


import repository.UserDTO;

import java.beans.PropertyChangeListener;
import java.util.Collection;

public class DomainController {

    private Club club;

    public DomainController(){      // DC is een façade die dingen doorgeeft aan de presentatielaag => Mag enkel dingen doorgeven, niet zelf methodes uitwerken.
        club = new Club();
    }

    public Collection<UserDTO> getFilteredMembers() {
        return club.getFilteredMembers();
    }

    public void filterUsers(String userName, int index){
        club.filterUsers(userName, index);
    }

    public void setCurrentUser(UserDTO user){
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
}
