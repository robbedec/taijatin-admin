package gui;

import domain.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import repository.UserDTO;

import javax.persistence.criteria.CriteriaBuilder;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetailPanelController extends VBox implements PropertyChangeListener {
    private DomainController dc;
    @FXML
    private TextField txtUsername, txtFirstname, txtLastname, txtEmail, txtGsm, txtNationalNumber, txtTelephone, txtCountry, txtPlace, txtZipcode, txtStreet, txtNumber, txtBornIn, txtBus;
    @FXML
    private DatePicker txtBirthday, txtRegistrationDate;
    @FXML
    private ChoiceBox txtGrade, txtGender, txtType, txtFormula;
    @FXML
    private Button btnSave, btnAdd;
    private UserDTO user;

    public DetailPanelController(DomainController dc) {
        this.dc = dc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // Validate number when user starts typing to set birthday and gender
        txtNationalNumber.textProperty().addListener((observableValue, s, t1) -> {
            user.setNationalInsuranceNumber(observableValue.getValue());
            ObservableList genders = FXCollections.observableArrayList("Selecteer", "Man", "Vrouw", "Trans");
            try {
                txtGender.setValue(genders.get(user.getGender()));
                txtBirthday.setValue(user.getBirthday().toLocalDate());
            } catch (NullPointerException ex) {
                return;
            }
        });
    }

    public void updateUser() {
        try {
            user.setUserName(txtUsername.getText());
            user.setFirstname(txtFirstname.getText());
            user.setLastname(txtLastname.getText());
            user.setEmail(txtEmail.getText());
            user.setBirthday(java.sql.Date.valueOf(txtBirthday.getValue()));
            user.setBirthday(java.sql.Date.valueOf(txtRegistrationDate.getValue()));
            user.setType(txtType.getValue().toString());
            //Get the gender int back from the list of genders
            ObservableList genders = txtGender.getItems();
            int newGender = genders.indexOf(txtGender.getValue());
            user.setGender(newGender);
            //Get the grade int back
            ObservableList grades = txtGrade.getItems();
            int newGrade = grades.indexOf(txtGrade.getValue());
            user.setGrade(newGrade);
            user.setMobilePhoneNumber(txtGsm.getText());
            user.setNationalInsuranceNumber(txtNationalNumber.getText());
            user.setPhoneNumber(txtTelephone.getText());
            //Update the address
            Address address = user.getAddressByAddressId();
            if (address == null) { // new address for new user
                address = new Address();
                address.setCountry(txtCountry.getText());
                address.setCity(txtPlace.getText());
                Integer zip = Integer.parseInt(txtZipcode.getText());
                address.setZipCode(zip);
                address.setStreet(txtStreet.getText());
                Integer number = Integer.parseInt(txtNumber.getText());
                address.setNumber(number);
                address.setBus(txtBus.getText());
            } else {
                if (!txtCountry.getText().equals(address.getCountry())) {
                    address.setCountry(txtCountry.getText());
                }
                if (!txtPlace.getText().equals(address.getCountry())) {
                    address.setCity(txtPlace.getText());
                }
                Integer zip = Integer.parseInt(txtZipcode.getText());
                if (!zip.equals(address.getZipCode())) {
                    address.setZipCode(zip);
                }
                if (txtStreet.getText().isEmpty() || !txtStreet.getText().equals(address.getStreet())) {
                    address.setStreet(txtStreet.getText());
                }
                Integer number = Integer.parseInt(txtNumber.getText());
                if (!number.equals(address.getNumber())) {
                    address.setNumber(number);
                }
                if (!txtBus.getText().equals(address.getBus())) {
                    address.setBus(txtBus.getText());
                }
            }
            user.setAddressByAddressId(address);
            user.setBornIn(txtBornIn.getText());
            Formula formula = user.getFormulasByFormulaId();
            if (formula == null) {
                formula = new Formula();
                formula.setFormulaName(txtFormula.getValue().toString());
            } else {
                if (!txtFormula.getValue().toString().equals(formula.getFormulaName())) {
                    formula.setFormulaName(txtFormula.getValue().toString());
                }
            }
            dc.setCurrentUser(user);
            dc.updateUser();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Succes", ButtonType.OK);
            alert.setTitle("De activiteit is succesvol opgeslagen.");
            alert.showAndWait().ifPresent(type -> {
                if(type == ButtonType.OK){
                    dc.setCurrentUser(user);
                    disableAllFields();
                }
            });
        } catch (CRuntimeException ex) {
            System.out.println("\nError updating/Creating user: " + ex.getMessage() + "\n");
            Alert error = new Alert(Alert.AlertType.ERROR, "Error updaten/creëren gebruiker: " + ex.getMessage(), ButtonType.OK);
            error.setHeaderText("Validatie errors");
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.showAndWait();
        } catch (NullPointerException np) {
            System.out.println("\nNullPointerExceptoin: no fields touched.\n");
            Alert error = new Alert(Alert.AlertType.ERROR, "Vul alle velden in alstublieft.", ButtonType.OK);
            error.setHeaderText("Validatie errors");
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.showAndWait();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        IUser u = (User)evt.getNewValue();
        if (u != null) {
            this.user = u.toUserDTO((User) u);
        } else {
            this.user = null;
        }
        if(this.user != null) {
            txtUsername.setText(user.getUserName());
            if(this.user.getUserName() == null || this.user.getUserName().equals("") || this.user.getUserName().equals("gebruikersnaam")){
                txtUsername.setEditable(true);
                btnAdd.setVisible(true);
                btnSave.setVisible(false);
            }
            else {
                txtUsername.setEditable(false);
                btnAdd.setVisible(false);
                btnSave.setVisible(true);
            }
            txtFirstname.setText(user.getFirstname());
            txtFirstname.setDisable(false);
            txtLastname.setText(user.getLastname());
            txtLastname.setDisable(false);
            txtEmail.setText(user.getEmail());
            txtEmail.setDisable(false);
            txtBirthday.setValue(user.getBirthday().toLocalDate());
            txtBirthday.setDisable(false);
            txtRegistrationDate.setValue(user.getRegistrationdate().toLocalDate());
            txtRegistrationDate.setDisable(false);
            txtType.setItems(FXCollections.observableArrayList("Member", "Teacher", "Admin"));
            txtType.setValue(user.getType());
            txtType.setDisable(false);
            //This will show the correct gender for each user
            ObservableList genders = FXCollections.observableArrayList("Selecteer", "Man", "Vrouw", "Trans");
            txtGender.setItems(genders);
            if (user.getGender() == null) {
                txtGender.setValue(genders.get(0));
            } else {
                txtGender.setValue(genders.get(user.getGender()));
            }
            txtGender.setDisable(false);
            //The correct grade of the user
            ObservableList grades = FXCollections.observableArrayList("Selecteer", "Zesde Kyu", "Vijfde Kyu", "Vierde Kyu", "Derde Kyu", "Tweede Kyu", "Eerste Kyu", "Eerste Dan", "Tweede Dan", "Derde Dan", "Vierde Dan", "Vijfde Dan", "Zesde Dan", "Zevende Dan", "Achtste Dan", "Negende Dan", "Tiende Dan", "Elfde Dan", "Twaalfde Dan");
            txtGrade.setItems(grades);
            txtGrade.setValue(grades.get(user.getGrade()));
            txtGrade.setDisable(false);
            txtGsm.setText(user.getMobilePhoneNumber());
            txtGsm.setDisable(false);
            txtNationalNumber.setText(user.getNationalInsuranceNumber());
            txtNationalNumber.setDisable(false);
            txtTelephone.setText(user.getPhoneNumber());
            txtTelephone.setDisable(false);
            txtCountry.setText(user.getAddressByAddressId().getCountry());
            txtCountry.setDisable(false);
            txtPlace.setText(user.getAddressByAddressId().getCity());
            txtPlace.setDisable(false);
            txtZipcode.setText(user.getAddressByAddressId().getZipCode().toString());
            txtZipcode.setDisable(false);
            txtStreet.setText(user.getAddressByAddressId().getStreet());
            txtStreet.setDisable(false);
            txtNumber.setText(user.getAddressByAddressId().getNumber().toString());
            txtNumber.setDisable(false);
            txtBus.setText(user.getAddressByAddressId().getBus());
            txtBus.setDisable(false);
            txtBornIn.setText(user.getBornIn());
            txtBornIn.setDisable(false);
            ObservableList formulas = FXCollections.observableArrayList("Geen", "DI_DO", "DI_ZA", "WO_ZA", "WO", "ZA", "ZO");
            txtFormula.setDisable(false);
            txtFormula.setItems(formulas);
            if(user.getFormulasByFormulaId() == null || user.getFormulasByFormulaId().getFormulaName().equals("")){
                txtFormula.setValue(formulas.get(0));
            }
            else {
                txtFormula.setValue(user.getFormulasByFormulaId().getFormulaName());
            }
        }
        else if(user == null){
            disableAllFields();
        }
    }

    private void disableAllFields(){
        btnSave.setVisible(false);
        btnAdd.setVisible(false);
        txtUsername.setDisable(true);
        txtEmail.setDisable(true);
        txtFirstname.setDisable(true);
        txtLastname.setDisable(true);
        txtRegistrationDate.setDisable(true);
        txtBirthday.setDisable(true);
        txtType.setDisable(true);
        txtGender.setDisable(true);
        txtGrade.setDisable(true);
        txtGsm.setDisable(true);
        txtNationalNumber.setDisable(true);
        txtTelephone.setDisable(true);
        txtCountry.setDisable(true);
        txtPlace.setDisable(true);
        txtZipcode.setDisable(true);
        txtStreet.setDisable(true);
        txtNumber.setDisable(true);
        txtBornIn.setDisable(true);
        txtFormula.setDisable(true);
        txtBus.setDisable(true);
    }
}
;