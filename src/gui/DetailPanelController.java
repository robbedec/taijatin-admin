package gui;

import domain.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class DetailPanelController extends GridPane implements PropertyChangeListener {
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtType;

    private User user;

    public DetailPanelController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.user = (User)evt.getNewValue();
        if(this.user != null){
            txtUsername.setText(user.getUserName());

        }
    }
}
