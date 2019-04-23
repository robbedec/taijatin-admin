package gui;

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
    private TextField txtUsername, txtFirstname, txtLastname, txtEmail;
    @FXML
    private DatePicker txtBirthday, txtRegistrationDate;
    @FXML
    private ChoiceBox txtGrade, txtGender, txtType;
    @FXML
    private Button btnSave, btnAdd;
    private User user;

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
        dc.setCurrentUser(user);
        dc.updateUser();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.user = (User)evt.getNewValue();
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
            txtGender.setValue(genders.get(user.getGender()));
            txtGender.setDisable(false);
            //The correct grade of the user
            ObservableList grades = FXCollections.observableArrayList("Selecteer", "Zesde Kyu", "Vijfde Kyu", "Vierde Kyu", "Derde Kyu", "Tweede Kyu", "Eerste Kyu", "Eerste Dan", "Tweede Dan", "Derde Dan", "Vierde Dan", "Vijfde Dan", "Zesde Dan", "Zevende Dan", "Achtste Dan", "Negende Dan", "Tiende Dan", "Elfde Dan", "Twaalfde Dan");
            txtGrade.setItems(grades);
            txtGrade.setValue(grades.get(user.getGrade()));
            txtGrade.setDisable(false);
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

        }
    }

    //Convert localdate to date
    /*private Date convertLocalDateToDate(DatePicker datePicker){
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        return date;
    }*/
}
;