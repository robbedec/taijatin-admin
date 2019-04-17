package gui;

import domain.DomainController;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import javafx.scene.input.KeyEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Arrays;

public class OverviewPanelController extends FlowPane implements PropertyChangeListener {

    private final DomainController dc;
    public final String[] types = new String[]{ "Geen filter", "Lid", "Leraar", "Beheerder" };

    @FXML
    private Button btnFilter;
    @FXML
    private ListView<User> listViewMembers;
    @FXML
    private TextField txtFilter;

    @FXML
    private ComboBox cboType;

    public OverviewPanelController(DomainController dc) {
        this.dc = dc;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("OverviewPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        listViewMembers.setItems((ObservableList) dc.getFilteredMembers());
        listViewMembers.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if(newValue != null) {
                if(oldValue == null || !oldValue.equals(newValue)) {
                    User user = newValue;
                    dc.setCurrentUser(user);
                }
            }
        });
        cboType.setItems(FXCollections.observableList(Arrays.asList(types)));
        cboType.getSelectionModel().selectedItemProperty().addListener(x -> {
            filter();
        });
        cboType.getSelectionModel().select(0);
    }

    @FXML
    private void filter() {
        dc.filter(txtFilter.getText(), cboType.getSelectionModel().getSelectedIndex());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        /*User user = (User)evt.getNewValue();
        System.out.println("kut");
        dc.setCurrentUser(listViewMembers.getSelectionModel().getSelectedItem());
        dc.setCurrentUser(user);

        if(user != null){
            System.out.println(user.getUserName());
        }

        System.out.println(dc.currentUser.getUserName());
        System.out.println(user.getUserName());*/
    }
}