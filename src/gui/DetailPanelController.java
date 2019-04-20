package gui;

import domain.DomainController;
import domain.User;
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
    private Button btnSave;
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
        user.setFirstname(txtFirstname.getText());
        user.setLastname(txtLastname.getText());
        user.setEmail(txtEmail.getText());
        user.setBirthday((java.sql.Date) convertLocalDateToDate(txtBirthday));
        user.setBirthday((java.sql.Date) convertLocalDateToDate(txtRegistrationDate));
        dc.setCurrentUser(user);
        dc.updateUser();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.user = (User)evt.getNewValue();
        if(this.user != null){
            txtUsername.setText(user.getUserName());
            txtFirstname.setText(user.getFirstname());
            txtLastname.setText(user.getLastname());
            txtEmail.setText(user.getEmail());
            txtBirthday.setValue(user.getBirthday().toLocalDate());
            txtRegistrationDate.setValue(user.getRegistrationdate().toLocalDate());
        }
    }

    //Convert localdate to date
    private Date convertLocalDateToDate(DatePicker datePicker){
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        return date;
    }
}
;