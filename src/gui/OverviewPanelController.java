package gui;

import domain.DomainController;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import javafx.scene.input.KeyEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

public class OverviewPanelController extends FlowPane implements PropertyChangeListener {

    private final DomainController dc;
    public final String[] types = new String[]{ "Geen filter", "Lid", "Leraar", "Beheerder" };
    public final String[] overzichten = new String[]{ "Activiteiten", "Inschrijvingen", "Aanwezigheden", "Clubkampioenschap", "Raadplegingen lesmateriaal" };

    @FXML
    private Button btnFilter;
    @FXML
    private ListView<User> listViewMembers;
    @FXML
    private TextField txtFilter;

    @FXML
    private ComboBox cboType, cboOverzicht;
    @FXML
    private Button btnNew, btnDelete;

    private ObservableList<User> users;

        public OverviewPanelController(DomainController dc) {
        this.dc = dc;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("OverviewPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        users = (ObservableList) dc.getFilteredMembers();
        listViewMembers.setItems(users);
        listViewMembers.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if(newValue != null) {
                if(oldValue == null || !oldValue.equals(newValue)) {
                    User user = newValue;
                    dc.setCurrentUser(user);
                }
            }
        });
        cboType.setItems(FXCollections.observableList(Arrays.asList(types)));
        cboType.getSelectionModel().selectedItemProperty().addListener(x -> {
            filter();
        });
        cboType.getSelectionModel().select(0);

        cboOverzicht.setItems(FXCollections.observableList(Arrays.asList(overzichten)));
        cboOverzicht.getSelectionModel().selectedItemProperty().addListener(x -> {

        });
    }

    @FXML
    private void filter() {
        dc.filterUsers(txtFilter.getText(), cboType.getSelectionModel().getSelectedIndex());
    }

    @FXML
    public void deleteUser(){
        int index = listViewMembers.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        dc.deleteUser();
    }

    @FXML
    public void newUser(){
        User newUser = new User();
        Date defaultBirthDay = Date.valueOf(LocalDate.of(1920, 1, 1));
        Date registrationDate = Date.valueOf(LocalDate.now());

        newUser.setBirthday(defaultBirthDay);
        newUser.setRegistrationdate(registrationDate);
        newUser.setGrade(1);
        newUser.setType("Member");
        dc.setCurrentUser(newUser);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        /*User user = (User)evt.getNewValue();
        System.out.println("kut");
        dc.setCurrentUser(listViewMembers.getSelectionModel().getSelectedItem());
        dc.setCurrentUser(user);

        if(user != null){
            System.out.println(user.getUserName());
        }

        System.out.println(dc.currentUser.getUserName());
        System.out.println(user.getUserName());*/
    }
}
