package gui;

import domain.DomainController;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.util.stream.Collectors;

public class OverviewPanelController extends FlowPane {

    private final DomainController dc;

    @FXML
    private Button btnFilter;
    @FXML
    private ListView<String> listViewMembers;
    @FXML
    private TextField txtFilter;

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
    }

    @FXML
    private void filter(KeyEvent event) {
        dc.filter(txtFilter.getText());
        System.out.println(txtFilter.getText());
    }
}
