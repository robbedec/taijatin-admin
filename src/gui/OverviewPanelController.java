package gui;

import domain.DomainController;
import domain.Grade;
import domain.User;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

import javafx.scene.input.KeyEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

public class OverviewPanelController extends FlowPane {

    private final DomainController dc;
    public final String[] types = new String[]{ "Geen filter", "Lid", "Leraar", "Beheerder" };
    public final String[] overzichten = new String[]{ "Activiteiten", "Inschrijvingen", "Aanwezigheden", "Clubkampioenschap", "Raadplegingen lesmateriaal" };

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> usernameCol, typeCol, gradeCol;
    @FXML
    private TextField txtFilter;
    @FXML
    private ComboBox cboType, cboOverzicht;
    @FXML
    private Button btnNew, btnDelete;

    private ObservableList<User> users;

    /**
     * @param dc
     */
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
        cboType.setItems(FXCollections.observableList(Arrays.asList(types)));
        cboType.getSelectionModel().selectedItemProperty().addListener(x -> {
            filter();
        });
        cboType.getSelectionModel().select(0);

        // Databinding user properties to table column
        userTable.setPlaceholder(new Label("Geen gebruikers gevonden"));
        usernameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUserName()));
        typeCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getType()));
        gradeCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Grade.valueOf(cellData.getValue().getGrade())));
        userTable.setItems((ObservableList)dc.getFilteredMembers());
        userTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if(newValue != null) {
                if(oldValue == null || !oldValue.equals(newValue)) {
                    User user = newValue;
                    dc.setCurrentUser(user);
                }
            }
        });
        }

    @FXML
    private void filter() {
        dc.filterUsers(txtFilter.getText(), cboType.getSelectionModel().getSelectedIndex());
    }

    @FXML
    public void deleteUser(){
        int index = userTable.getSelectionModel().getSelectedIndex();
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
}
