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
    private Button btnSave, btnAdd, btnRegister, btnUndoRegister, btnRefresh, btnAddNoMember;
    private ActivityDTO activity;

    private ObservableList<IUser> registeredUsers, notRegisteredUsers;

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

        listViewNotRegistered.setPlaceholder(new Label("Geen (niet-)leden meer gevonden"));
        listViewRegistered.setPlaceholder(new Label("Geen gebruikers geregistreerd"));
        notRegisteredUsers = (ObservableList) dc.getNotRegisteredUsersFromActivity();
        registeredUsers = (ObservableList) dc.getRegisteredUsersFromActivity();
        listViewNotRegistered.setItems(notRegisteredUsers);
        listViewRegistered.setItems(registeredUsers);
    }

    public void updateActivity() {
        try {
            activity.setName(txtName.getText());
            ObservableList types = txtType.getItems();
            int newType = types.indexOf(txtType.getValue());
            activity.setType(newType);
            activity.setInfo(txtInfo.getText());
            activity.setStatus(cbStatus.isSelected());
            activity.setMaxNumberOfParticipants((int) sliderMax.getValue());
            int total = Integer.parseInt(txtTotal.getText());
            activity.setNumberOfParticipants(total);
            dc.setCurrentActivity(activity);
            dc.updateActivity();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Succes", ButtonType.OK);
            alert.setTitle("De activiteit is succesvol opgeslagen.");
            alert.showAndWait().ifPresent(type -> {
                if(type == ButtonType.OK){
                    disableAllFields();
                }
            });
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
        int index = listViewNotRegistered.getSelectionModel().getSelectedIndex();
        if(!dc.isFullActivity()) {
            dc.register(index);
            notRegisteredUsers = (ObservableList) dc.getNotRegisteredUsersFromActivity();
            setTotalRegistered();
            listViewNotRegistered.setItems(notRegisteredUsers);
        }
        else {
            showAlertFull();
        }
    }

    public void undoRegister(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK, ButtonType.NO);
        alert.setTitle("Maak registratie ongedaan");
        alert.setContentText("Bevestig of je gebruiker " + listViewRegistered.getSelectionModel().getSelectedItem().getUserName() + " terug wilt uitschrijven.");
        alert.showAndWait().ifPresent(type -> {
            if(type == ButtonType.OK){
                int index = listViewRegistered.getSelectionModel().getSelectedIndex();
                dc.undoRegister(index);
                notRegisteredUsers = (ObservableList) dc.getNotRegisteredUsersFromActivity();
                setTotalRegistered();
                listViewNotRegistered.setItems(notRegisteredUsers);
            }
            else {
                System.out.println("Gebruiker " + listViewRegistered.getSelectionModel().getSelectedItem() + " is niet uitgeschreven.");
            }
        });
    }

    public void refresh(){
        dc.refrestNotRegisteredList(activity);
        notRegisteredUsers = (ObservableList) dc.getNotRegisteredUsersFromActivity();
        listViewNotRegistered.setItems(notRegisteredUsers);
    }

    public void addNoMember() {
        if (!dc.isFullActivity()) {
            TextInputDialog textInputDialog = new TextInputDialog("Naam");
            textInputDialog.setTitle("Voeg een niet-lid toe");
            textInputDialog.setHeaderText("Geef een naam in");
            textInputDialog.setContentText("Naam");
            textInputDialog.showAndWait().ifPresent(name -> {
                if(!dc.isRegistered(name)){
                    IUser u = new User(name);
                    dc.addNoMember(activity, (User) u);
                    setTotalRegistered();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Reeds geregistreerde naam");
                    alert.setContentText("Deze naam is reeds geregistreerd. Registreer een andere naam voor het niet-lid.");
                    alert.showAndWait();
                }

            });
        } else {
            showAlertFull();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        IActivity act = (Activity) evt.getNewValue();
        if (act != null) {
            this.activity = act.toActivityDTO((Activity) act);
        } else {
            this.activity = null;
        }
        if (this.activity != null) {
            btnRegister.setVisible(true);
            btnUndoRegister.setVisible(true);
            btnAddNoMember.setVisible(true);
            btnRefresh.setVisible(true);
            if(this.activity.getName() == null || this.activity.getName().equals("")){
                txtName.setEditable(true);
                btnAdd.setVisible(true);
                btnSave.setVisible(false);
            }
            else {
                txtName.setEditable(false);
                btnAdd.setVisible(false);
                btnSave.setVisible(true);
            }
            txtName.setText(this.activity.getName());
            txtName.setDisable(false);
            ObservableList types = FXCollections.observableArrayList(TypeOfActivity.values());
            txtType.setItems(types);
            txtType.setValue(types.get(this.activity.getType()));
            txtType.setDisable(false);
            cbStatus.setSelected(this.activity.getStatus());
            cbStatus.setDisable(false);
            sliderMax.setDisable(false);
            sliderMax.setBlockIncrement(1);
            sliderMax.setMin(0);
            sliderMax.setMax(40);
            sliderMax.setValue(this.activity.getMaxNumberOfParticipants());
            txtSlider.setText(String.valueOf(this.activity.getMaxNumberOfParticipants()));
            txtSlider.setDisable(false);
            txtSlider.setEditable(false);
            txtTotal.setText(String.valueOf(this.activity.getNumberOfParticipants()));
            txtTotal.setDisable(false);
            txtTotal.setEditable(false);
            txtInfo.setText(this.activity.getInfo());
            txtInfo.setDisable(false);
            notRegisteredUsers = (ObservableList) dc.getNotRegisteredUsersFromActivity();
            registeredUsers = (ObservableList) dc.getRegisteredUsersFromActivity();
            listViewNotRegistered.setItems(notRegisteredUsers);
            listViewNotRegistered.setDisable(false);
            listViewRegistered.setItems(registeredUsers);
            listViewRegistered.setDisable(false);
        }
        else if(act == null){
            disableAllFields();
        }
    }

    private void disableAllFields(){
        btnSave.setVisible(false);
        btnAdd.setVisible(false);
        btnRefresh.setVisible(false);
        btnAddNoMember.setVisible(false);
        btnRegister.setVisible(false);
        btnUndoRegister.setVisible(false);
        txtName.setDisable(true);
        txtType.setDisable(true);
        cbStatus.setDisable(true);
        txtTotal.setDisable(true);
        txtInfo.setDisable(true);
        sliderMax.setDisable(true);
        txtSlider.setDisable(true);
        listViewNotRegistered.setDisable(true);
        listViewRegistered.setDisable(true);
    }

    private void showAlertFull(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Volzet");
        alert.setContentText("Deze activiteit zit vol.");
        alert.showAndWait();
    }

    private void setTotalRegistered(){
        int total = dc.getTotalRegistered();
        txtTotal.setText(String.valueOf(total));
        registeredUsers = (ObservableList) dc.getRegisteredUsersFromActivity();
        listViewRegistered.setItems(registeredUsers);
    }
}
