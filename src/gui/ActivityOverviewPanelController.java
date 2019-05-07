package gui;

import domain.Activity;
import domain.DomainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.List;

public class ActivityOverviewPanelController<T> extends FlowPane {
    private final DomainController dc;

    @FXML
    private TextField txtFilter;
    @FXML
    private ComboBox cboType, cboOverzicht;
    @FXML
    private Button btnNew, btnDelete;

    @FXML
    private FlowPane flowpane;

    private TableViewFactory tableViewFactory;

    public ActivityOverviewPanelController(DomainController dc, List<T> enumInstances) {
        this.dc = dc;
        this.tableViewFactory = new TableViewFactory(dc);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ActivityOverviewPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        enumInstances.forEach(typeOfActivity -> cboType.getItems().add(typeOfActivity.toString()));
        cboType.getSelectionModel().selectedItemProperty().addListener(x -> {
            filter();
        });
        cboType.getSelectionModel().select(0);

        flowpane.getChildren().add(2, tableViewFactory.getActityTableView());

    }

    @FXML
    private void filter() {
        dc.filterActivities(txtFilter.getText(), cboType.getSelectionModel().getSelectedIndex());
    }

    @FXML
    public void deleteActivity(){
        TableView<Activity> tableView = (TableView)flowpane.getChildren().get(2);
        int index = tableView.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ben je zeker dat je de activiteit " + tableView.getSelectionModel().getSelectedItem().getName() + " wilt verwijderen?", ButtonType.OK, ButtonType.NO );
        alert.setTitle("Verwijder activiteit");
        alert.setContentText("Bevestig of je dit wilt verwijderen.");
        alert.showAndWait().ifPresent(type -> {
            if(type == ButtonType.OK){
                dc.deleteActivity();
            }
            else {
                System.out.println("Activiteit " + tableView.getSelectionModel().getSelectedItem().getName() + " is niet verwijderd.");
            }
        });
    }

    @FXML
    public void newActivity(){
        Activity newActivity = new Activity();
        newActivity.setName("");
        newActivity.setInfo("");
        newActivity.setMaxNumberOfParticipants(10);
        newActivity.setStatus(false);
        newActivity.setType(0);
        dc.setCurrentActivity(newActivity);
    }
}
