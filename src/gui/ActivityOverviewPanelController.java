package gui;

import domain.Activity;
import domain.DomainController;
import domain.TypeOfActivity;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import repository.ActivityDTO;

import java.io.IOException;
import java.util.Arrays;

public class ActivityOverviewPanelController extends FlowPane {
    private DomainController dc;

    @FXML
    private TableView<ActivityDTO> activityTable;
    @FXML
    private TableColumn<ActivityDTO, String> nameCol, typeCol;
    @FXML
    private TextField txtFilter;
    @FXML
    private ComboBox cboType, cboOverzicht;
    @FXML
    private Button btnNew, btnDelete;

    private ObservableList<ActivityDTO> activities;

    public ActivityOverviewPanelController(DomainController dc) {
        this.dc = dc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ActivityOverviewPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        activities = (ObservableList) dc.getFilteredActivities();
        cboType.setItems(FXCollections.observableList(Arrays.asList(dc.getTypesOfActivity())));
        cboType.getSelectionModel().selectedItemProperty().addListener(x -> {
            filter();
        });
        cboType.getSelectionModel().select(0);

        //Databinding activity props to table column
       activityTable.setPlaceholder(new Label("Geen activiteiten gevonden"));
        nameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        typeCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(TypeOfActivity.valueOf(cellData.getValue().getType())));
        activityTable.setItems((ObservableList) dc.getFilteredActivities());
        activityTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (newValue != null) {
                if (oldValue == null || !oldValue.equals(newValue)) {
                    ActivityDTO aDTO = newValue;
                    dc.setCurrentActivity(aDTO);
                }
            }
        });
    }

    @FXML
    private void filter() {
        dc.filterActivities(txtFilter.getText(), cboType.getSelectionModel().getSelectedIndex());
    }

    @FXML
    public void deleteActivity(){
        int index = activityTable.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ben je zeker dat je de activiteit " + activityTable.getSelectionModel().getSelectedItem().getName() + " wilt verwijderen?", ButtonType.OK, ButtonType.NO );
        alert.setTitle("Verwijder activiteit");
        alert.setContentText("Bevestig of je dit wilt verwijderen.");
        alert.showAndWait().ifPresent(type -> {
            if(type == ButtonType.OK){
                dc.deleteActivity();
            }
            else {
                System.out.println("Activiteit " + activityTable.getSelectionModel().getSelectedItem().getName() + " is niet verwijderd.");
            }
        });
    }

    @FXML
    public void newActivity(){
        ActivityDTO newActivity = new ActivityDTO();
        newActivity.setName("");
        dc.setCurrentActivity(newActivity);
    }
}
