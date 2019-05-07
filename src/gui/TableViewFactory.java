package gui;

import domain.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import repository.UserDTO;

public class TableViewFactory<T> {

    private TableView<T> tableView = new TableView<>();
    private DomainController dc;

    public TableViewFactory(DomainController dc) {
        this.dc = dc;
        tableView.setPrefHeight(725);
        tableView.setMaxWidth(400);
        tableView.setPrefWidth(400);
    }

    public TableView<T> getUserTableView() {

        tableView.setPlaceholder(new Label("Geen gebruikers gevonden"));

        TableColumn<User, String> usernameCol = new TableColumn<>("Gebruikersnaam");
        usernameCol.setPrefWidth(200);
        usernameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUserName()));

        TableColumn<User, String> gradeCol = new TableColumn<>("Graad");
        gradeCol.setPrefWidth(100);
        gradeCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Grade.valueOf(cellData.getValue().getGrade())));

        TableColumn<User, String> formulaCol = new TableColumn<>("Formule");
        formulaCol.setPrefWidth(100);
        formulaCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFormulasByFormulaId() == null ? "" : cellData.getValue().getFormulasByFormulaId().getFormulaName()));

        tableView.getColumns().add((TableColumn<T, ?>) usernameCol);
        tableView.getColumns().add((TableColumn<T, ?>) gradeCol);
        tableView.getColumns().add((TableColumn<T, ?>) formulaCol);

        tableView.setItems((ObservableList)dc.getFilteredMembers());
        tableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if(newValue != null) {
                if(oldValue == null || !oldValue.equals(newValue)) {
                    User u = (User)newValue;
                    UserDTO uDto = u.toUserDTO(u);
                    dc.setCurrentUser(uDto);
                }
            }
        });
        return tableView;
    }

    public TableView<T> getActityTableView() {
        tableView.setPlaceholder(new Label("Geen activiteiten gevonden"));

        TableColumn<Activity, String> nameCol = new TableColumn<>("Naam");
        nameCol.setPrefWidth(175);
        nameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));

        TableColumn<Activity, String> typeCol = new TableColumn<>("Type");
        typeCol.setPrefWidth(75);
        typeCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(TypeOfActivity.valueOf(cellData.getValue().getType())));

        TableColumn<Activity, String> statusCol = new TableColumn<>("Status");
        statusCol.setPrefWidth(50);
        statusCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getStatus())));

        TableColumn<Activity, String> numberCol = new TableColumn<>("Total registered");
        numberCol.setPrefWidth(100);
        numberCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getNumberOfParticipants())));

        tableView.getColumns().add((TableColumn<T, ?>) nameCol);
        tableView.getColumns().add((TableColumn<T, ?>) typeCol);
        tableView.getColumns().add((TableColumn<T, ?>) statusCol);
        tableView.getColumns().add((TableColumn<T, ?>) numberCol);

    tableView.setItems((ObservableList)dc.getFilteredActivities());
        tableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if(newValue != null) {
                if(oldValue == null || !oldValue.equals(newValue)) {
                    Activity a = (Activity) newValue;
                    dc.setCurrentActivity(a);
                }
            }
        });
        return tableView;
    }
}
