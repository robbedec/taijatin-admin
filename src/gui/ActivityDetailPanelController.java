package gui;

import domain.Address;
import domain.CRuntimeException;
import domain.DomainController;
import domain.Formula;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import repository.ActivityDTO;
import repository.UserDTO;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class ActivityDetailPanelController extends VBox implements PropertyChangeListener {
    private DomainController dc;
    @FXML
    private TextField txtName;
    @FXML
    private TextArea txtInfo;
    @FXML
    private ChoiceBox txtType;
    @FXML
    private Button btnSave, btnAdd;
    private ActivityDTO activity;

    public ActivityDetailPanelController(DomainController dc) {
        this.dc = dc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ActivityDetailPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateActivity() {
        try {
            activity.setName(txtName.getText());
            ObservableList types = txtType.getItems();
            int newType = types.indexOf(txtType.getValue());
            activity.setType(newType);
            dc.setCurrentActivity(activity);
            dc.updateActivity();
        } catch (CRuntimeException ex) {
            System.out.println("\nError updating/Creating user: " + ex.getMessage() + "\n");
        } catch (NullPointerException np) {
            System.out.println("\nNullPointerExceptoin: no fields touched.\n");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.activity = (ActivityDTO) evt.getNewValue();
        if (activity != null) {
            txtName.setText(activity.getName());
            txtName.setEditable(true);
            txtType.setItems(FXCollections.observableArrayList("Uitstap", "Stage"));
            txtType.setValue(activity.getType());
            txtType.setDisable(false);
            txtInfo.setText(activity.getInfo());
            txtInfo.setEditable(true);
        }
        else if(activity == null){
            txtName.setEditable(false);
            txtType.setDisable(true);
            txtInfo.setEditable(false);
        }
    }
}
;