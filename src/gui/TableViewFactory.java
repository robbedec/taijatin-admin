package gui;

import domain.DomainController;
import domain.Grade;
import domain.User;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
                    User uDto = (User)newValue;
                    dc.setCurrentUser(uDto);
                }
            }
        });
        return tableView;
    }

    public TableView<T> getActityTableView() {
        return null;
    }
}
