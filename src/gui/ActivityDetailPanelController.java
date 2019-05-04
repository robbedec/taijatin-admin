package gui;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import repository.ActivityDTO;
import repository.UserDTO;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class ActivityDetailPanelController extends VBox implements PropertyChangeListener {
    private DomainController dc;
    @FXML
    private ListView<IUser> listViewNotRegistered, listViewRegistered;
    @FXML
    private TextField txtName, txtTotal, txtSlider;
    @FXML
    private TextArea txtInfo;
    @FXML
    private Slider sliderMax;
    @FXML
    private CheckBox cbStatus;
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
            activity.setInfo(txtInfo.getText());
            activity.setMaxNumberOfParticipants((int) sliderMax.getValue());
            dc.setCurrentActivity(activity);
            dc.updateActivity();
        } catch (CRuntimeException ex) {
            System.out.println("\nError updaten/creëren activiteit: " + ex.getMessage() + "\n");
        } catch (NullPointerException np) {
            System.out.println("\nNullPointerExceptoin: Geen velden ingevuld.\n");
            Alert error = new Alert(Alert.AlertType.ERROR, "Vul alle velden in alstublieft.", ButtonType.OK);
            error.setHeaderText("Validatie errors");
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.showAndWait();
        }
    }

    public void slide(){
        Double d = sliderMax.getValue();
        int i = d.intValue();
        txtSlider.setText(String.valueOf(i));
    }

    public void register(){

    }

    public void undoRegister(){

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
            cbStatus.setSelected(activity.getStatus());
            cbStatus.setDisable(false);
            sliderMax.setBlockIncrement(1);
            sliderMax.setMin(0);
            sliderMax.setMax(20);
            sliderMax.setValue(activity.getMaxNumberOfParticipants());
            txtSlider.setText(String.valueOf(activity.getMaxNumberOfParticipants()));
            txtSlider.setEditable(false);
            txtTotal.setText(String.valueOf(activity.getNumberOfParticipants()));
            txtTotal.setEditable(false);
            txtInfo.setText(activity.getInfo());
            txtInfo.setEditable(true);
        }
        else if(activity == null){
            txtName.setEditable(false);
            txtType.setDisable(true);
            cbStatus.setDisable(true);
            txtTotal.setEditable(false);
            txtInfo.setEditable(false);
        }
    }
}
;