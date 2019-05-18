package gui;

import domain.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

import javafx.scene.layout.Region;
import repository.UserDTO;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class OverviewPanelController<T> extends FlowPane {

    private final DomainController dc;

    @FXML
    private TextField txtFilter;
    @FXML
    private ComboBox cboType, cboGrade, cboOverzicht;
    @FXML
    private Button btnNew, btnDelete;

    @FXML
    private FlowPane flowpane;

    private TableViewFactory factory;

    /**
     * @param dc
     */
    public OverviewPanelController(DomainController dc, List<T> enumInstances1, List<T> enumInstances2) {
        this.dc = dc;
        this.factory = new TableViewFactory(dc);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("OverviewPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        enumInstances1.forEach(type -> cboType.getItems().add(type.toString()));
        enumInstances2.forEach(grade -> cboGrade.getItems().add(grade.toString()));
        cboType.getSelectionModel().selectedItemProperty().addListener(x -> filter());
        cboType.getSelectionModel().select(0);
        cboGrade.getSelectionModel().selectedItemProperty().addListener(x -> filter());
        cboGrade.getSelectionModel().select(0);

        flowpane.getChildren().add(3, factory.getUserTableView());
    }

    @FXML
    private void filter() {
        dc.filterUsers(txtFilter.getText(), cboType.getSelectionModel().getSelectedIndex(), cboGrade.getSelectionModel().getSelectedIndex());
    }

    @FXML
    public void deleteUser(){
        TableView<User> tableView = (TableView)flowpane.getChildren().get(3);
        int index = tableView.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ben je zeker dat je de gebruiker " + tableView.getSelectionModel().getSelectedItem().getUserName() + " wilt verwijderen?", ButtonType.OK, ButtonType.NO );
        alert.setTitle("Verwijder gebruiker");
        alert.setHeaderText("Bevestig of je dit wilt verwijderen.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait().ifPresent(type -> {
            if(type == ButtonType.OK){
                dc.deleteUser();
            }
            else {
                System.out.println("Gebruiker " + tableView.getSelectionModel().getSelectedItem().getUserName() + " is niet verwijderd.");
            }
        });
    }

    @FXML
    public void newUser(){
        UserDTO newUser = new UserDTO();
        Date defaultBirthDay = Date.valueOf(LocalDate.of(1920, 1, 1));
        Date registrationDate = Date.valueOf(LocalDate.now());
        Address defaultAddress = new Address();
        defaultAddress.setCity("");
        defaultAddress.setCountry("");
        defaultAddress.setStreet("");
        defaultAddress.setNumber(1);
        defaultAddress.setZipCode(1000);
        defaultAddress.setBus("");

        Formula defaultFormula = new Formula();
        defaultFormula.setFormulaName("");

        newUser.setBirthday(defaultBirthDay);
        newUser.setRegistrationdate(registrationDate);
        newUser.setGrade(1);
        newUser.setType("Lid");
        newUser.setAddressByAddressId(defaultAddress);

        newUser.setUserName("Gebruikersnaam");
        newUser.setFirstname("Voornaam");
        newUser.setLastname("Naam");
        newUser.setEmail("naam@email.com");
        newUser.setGender(1);
        newUser.setBornIn("Geboorteplaats");
        newUser.setNationalInsuranceNumber("yy.mm.dd-123.45");
        newUser.setMobilePhoneNumber("+32");

        dc.setCurrentUser(newUser);

    }
}
