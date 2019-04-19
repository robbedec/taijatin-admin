package gui;

import domain.DomainController;
import domain.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class DetailPanelController extends GridPane implements PropertyChangeListener {
    private DomainController dc;
    @FXML
    private TextField txtUsername, txtFirstname, txtLastname, txtEmail;
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
        }
    }
}
