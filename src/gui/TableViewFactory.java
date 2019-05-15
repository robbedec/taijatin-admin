package gui;

import domain.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import repository.ActivityDTO;
import repository.UserDTO;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TableViewFactory<T> {

    private TableView<T> tableView = new TableView<>();
    private DomainController dc;

    public TableViewFactory(DomainController dc) {
        this.dc = dc;
    }

    private void setSmallSize() {
        tableView.setPrefHeight(725);
        tableView.setMaxWidth(400);
        tableView.setPrefWidth(400);
    }

    private void setBigSize() {
        tableView.setPrefHeight(725);
        tableView.setMaxWidth(1225);
        tableView.setPrefWidth(1225);
    }

    public TableView<T> getUserTableView() {
        tableView = new TableView<>();
        setSmallSize();
        tableView.setPlaceholder(new Label("Geen gebruikers gevonden"));

        TableColumn<User, String> usernameCol = new TableColumn<>("Gebruikersnaam");
        usernameCol.setPrefWidth(135);
        usernameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUserName()));

        TableColumn<User, String> gradeCol = new TableColumn<>("Graad");
        gradeCol.setPrefWidth(95);
        gradeCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Grades.valueOf(cellData.getValue().getGrade() + 1)));

        TableColumn<User, String> formulaCol = new TableColumn<>("Formule");
        formulaCol.setPrefWidth(76);
        formulaCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFormulasByFormulaId() == null ? "" : cellData.getValue().getFormulasByFormulaId().getFormulaName()));

        TableColumn<User, String> typeCol = new TableColumn<>("Type");
        typeCol.setPrefWidth(95);
        typeCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getType()));


        tableView.getColumns().add((TableColumn<T, ?>) usernameCol);
        tableView.getColumns().add((TableColumn<T, ?>) gradeCol);
        tableView.getColumns().add((TableColumn<T, ?>) formulaCol);
        tableView.getColumns().add((TableColumn<T, ?>) typeCol);

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
        tableView = new TableView<>();
        setSmallSize();
        tableView.setPlaceholder(new Label("Geen activiteiten gevonden"));

        TableColumn<Activity, String> nameCol = new TableColumn<>("Naam");
        nameCol.setPrefWidth(150);
        nameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));

        TableColumn<Activity, String> typeCol = new TableColumn<>("Type");
        typeCol.setPrefWidth(60);
        typeCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(TypeOfActivity.valueOf(cellData.getValue().getType())));

        TableColumn<Activity, String> statusCol = new TableColumn<>("Status");
        statusCol.setPrefWidth(60);
        statusCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStatus() ? "Actief" : "Inactief"));

        TableColumn<Activity, String> numberCol = new TableColumn<>("Is volzet?");
        numberCol.setPrefWidth(140);
        numberCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getNumberOfParticipants() >= cellData.getValue().getMaxNumberOfParticipants() ? "Volzet" : "Nog " + String.valueOf(cellData.getValue().getMaxNumberOfParticipants() - cellData.getValue().getNumberOfParticipants()) + " plaatsen vrij"));

        TableColumn<Activity, String> infoCol = new TableColumn<>("Info");
        infoCol.setPrefWidth(400);
        infoCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getInfo()));

        tableView.getColumns().add((TableColumn<T, ?>) nameCol);
        tableView.getColumns().add((TableColumn<T, ?>) typeCol);
        tableView.getColumns().add((TableColumn<T, ?>) statusCol);
        tableView.getColumns().add((TableColumn<T, ?>) numberCol);
        tableView.getColumns().add((TableColumn<T, ?>) infoCol);


        tableView.setItems((ObservableList)dc.getFilteredActivities());
        tableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if(newValue != null) {
                if(oldValue == null || !oldValue.equals(newValue)) {
                    Activity a = (Activity) newValue;
                    ActivityDTO aDto = a.toActivityDTO(a);
                    dc.setActivityUserLists(aDto);
                    dc.setCurrentActivity(aDto);
                }
            }
        });
        return tableView;
    }

   

    public TableView<T> getInschrijvingsTableView() {
        tableView = new TableView<>();
        setBigSize();
        tableView.setPlaceholder(new Label("Geen gebruikers gevonden"));

        TableColumn<User, String> usernameCol = new TableColumn<>("Gerbuikersnaam");
        usernameCol.setPrefWidth(200);
        usernameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUserName()));


        TableColumn<User, String> registrationDateCol = new TableColumn<>("Inschrijvingsdatum");
        registrationDateCol.setPrefWidth(200);
        registrationDateCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getRegistrationdate())));

        tableView.getColumns().addAll((TableColumn<T, ?>) usernameCol, (TableColumn<T, ?>) registrationDateCol);

        tableView.setItems((ObservableList)FXCollections.observableArrayList(dc.getFilteredMembers().stream().sorted(Comparator.comparing(User::getRegistrationdate).reversed().thenComparing(User::getUserName)).collect(Collectors.toList())));
        return tableView;
    }

    public TableView<T> getClubKamptioenschapTableView(){
        tableView = new TableView<>();
        setBigSize();
        tableView.setPlaceholder(new Label("Geen gebruikers gevonden"));

        TableColumn<User, String> usernameCol = new TableColumn<>("Gebruikersnaam");
        usernameCol.setPrefWidth(200);
        usernameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUserName()));

        TableColumn<User, String> scoreCol = new TableColumn<>("Score");
        scoreCol.setPrefWidth(200);
        scoreCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getScore())));

        tableView.getColumns().addAll((TableColumn<T, ?>) usernameCol, (TableColumn<T, ?>) scoreCol);

        tableView.setItems((ObservableList)FXCollections.observableArrayList(dc.getFilteredMembers().stream().filter(x -> x.getScore() != null).sorted(Comparator.comparing(User::getScore).reversed().thenComparing(User::getUserName)).collect(Collectors.toList())));
        return tableView;
    }

    public TableView<T> getBigActivityTableView(){
        TableView t = this.getActityTableView();
        setBigSize();
        return t;
    }
}
