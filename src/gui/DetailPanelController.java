package gui;

import domain.Address;
import domain.DomainController;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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

public class DetailPanelController extends GridPane implements PropertyChangeListener {
    private DomainController dc;
    @FXML
    private TextField txtUsername, txtFirstname, txtLastname, txtEmail, txtGsm, txtNationalNumber, txtTelephone, txtCountry, txtPlace, txtZipcode, txtStreet, txtNumber, txtBornIn;
    @FXML
    private DatePicker txtBirthday, txtRegistrationDate;
    @FXML
    private ChoiceBox txtGrade, txtGender, txtType;
    @FXML
    private Button btnSave, btnAdd;
    private UserDTO user;
    private Address address;

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
    }

    public void updateUser() {
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
        address = user.getAddressByAddressId();
        if (address == null) { // new address for new user
            address = new Address();
            address.setCountry(txtCountry.getText());
            address.setCity(txtPlace.getText());
            Integer zip = Integer.parseInt(txtZipcode.getText());
            address.setZipCode(zip);
            address.setStreet(txtStreet.getText());
            Integer number = Integer.parseInt(txtNumber.getText());
            address.setNumber(number);
        } else {
            if(!txtCountry.getText().equals(address.getCountry())) {
                address.setCountry(txtCountry.getText());
            }
            if(!txtPlace.getText().equals(address.getCountry())) {
                address.setCity(txtPlace.getText());
            }
            Integer zip = Integer.parseInt(txtZipcode.getText());
            if(!zip.equals(address.getZipCode())) {
                address.setZipCode(zip);
            }
            if(!txtStreet.getText().equals(address.getStreet())) {
                address.setStreet(txtStreet.getText());
            }
            Integer number = Integer.parseInt(txtNumber.getText());
            if(!number.equals(address.getNumber())) {
                address.setNumber(number);
            }
        }
        user.setAddressByAddressId(address);
        user.setBornIn(txtBornIn.getText());
        dc.setCurrentUser(user);
        dc.updateUser();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.user = (UserDTO)evt.getNewValue();
        if(this.user != null) {
            txtUsername.setText(user.getUserName());
            if(this.user.getUserName() == null || this.user.getUserName() == ""){
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
            txtFirstname.setEditable(true);
            txtLastname.setText(user.getLastname());
            txtLastname.setEditable(true);
            txtEmail.setText(user.getEmail());
            txtEmail.setEditable(true);
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
            txtGsm.setEditable(true);
            txtNationalNumber.setText(user.getNationalInsuranceNumber());
            txtNationalNumber.setEditable(true);
            txtTelephone.setText(user.getPhoneNumber());
            txtTelephone.setEditable(true);
            txtCountry.setText(user.getAddressByAddressId().getCountry());
            txtCountry.setEditable(true);
            txtPlace.setText(user.getAddressByAddressId().getCity());
            txtPlace.setEditable(true);
            txtZipcode.setText(user.getAddressByAddressId().getZipCode().toString());
            txtZipcode.setEditable(true);
            txtStreet.setText(user.getAddressByAddressId().getStreet());
            txtStreet.setEditable(true);
            txtNumber.setText(user.getAddressByAddressId().getNumber().toString());
            txtNumber.setEditable(true);
            txtBornIn.setText(user.getBornIn());
            txtBornIn.setEditable(true);
        }
        else if(user == null){
            btnSave.setVisible(false);
            btnAdd.setVisible(false);
            txtUsername.setEditable(false);
            txtEmail.setEditable(false);
            txtFirstname.setEditable(false);
            txtLastname.setEditable(false);
            txtRegistrationDate.setDisable(true);
            txtBirthday.setDisable(true);
            txtType.setDisable(true);
            txtGender.setDisable(true);
            txtGrade.setDisable(true);
            txtGsm.setEditable(false);
            txtNationalNumber.setEditable(false);
            txtTelephone.setEditable(false);
            txtCountry.setEditable(false);
            txtPlace.setEditable(false);
            txtZipcode.setEditable(false);
            txtStreet.setEditable(false);
            txtNumber.setEditable(false);
            txtBornIn.setEditable(false);
        }
    }
}
;