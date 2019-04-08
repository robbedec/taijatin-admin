package domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import repository.GenericDao;
import repository.GenericDaoJpa;

import java.util.Collection;
import java.util.stream.Collectors;

public class DomainController {

    private User currentUser;

    private GenericDao<User> userRepo;

    private ObservableList<User> userLijst;
    private FilteredList<User> filteredList;

    public DomainController(){
        userRepo = new GenericDaoJpa<>(User.class);
        userLijst = FXCollections.observableList(userRepo.getAll());
        filteredList = new FilteredList<>(userLijst, p -> true);
    }

    public ObservableList<String> getMembers() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(userRepo.getAll().stream().filter(x -> x.getType().equals("Member")).map(User::getUserName).collect(Collectors.toList())));
    }

    public Collection<User> getFilteredMembers() {
        return filteredList;
    }

    public void filter(String userName){
        filteredList.setPredicate(user -> user.getUserName().toLowerCase().startsWith(userName.toLowerCase()));
    }

    public void setCurrentUser(User user){
        this.currentUser = user;
        notifyAll();
    }

}
