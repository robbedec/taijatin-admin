package gui;

import domain.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

import javafx.scene.input.KeyEvent;
import repository.UserDTO;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

public class OverviewPanelController extends FlowPane {

    private final DomainController dc;
    public final String[] types = new String[]{ "Geen filter", "Lid", "Leraar", "Beheerder" };
    public final String[] overzichten = new String[]{ "Activiteiten", "Inschrijvingen", "Aanwezigheden", "Clubkampioenschap", "Raadplegingen lesmateriaal" };

    @FXML
    private TableView<UserDTO> userTable;
    @FXML
    private TableColumn<UserDTO, String> usernameCol, typeCol, gradeCol, formulaCol;
    @FXML
    private TextField txtFilter;
    @FXML
    private ComboBox cboType, cboOverzicht;
    @FXML
    private Button btnNew, btnDelete;

    private ObservableList<UserDTO> users;

    /**
     * @param dc
     */
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
        users = (ObservableList) dc.getFilteredMembers();
        cboType.setItems(FXCollections.observableList(Arrays.asList(dc.getTypesOfUser())));
        cboType.getSelectionModel().selectedItemProperty().addListener(x -> {
            filter();
        });
        cboType.getSelectionModel().select(0);

        // Databinding user properties to table column
        userTable.setPlaceholder(new Label("Geen gebruikers gevonden"));
        usernameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUserName()));
        typeCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getType()));
        gradeCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Grade.valueOf(cellData.getValue().getGrade())));
        formulaCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFormulasByFormulaId().getFormulaName()));
        userTable.setItems((ObservableList)dc.getFilteredMembers());
        userTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if(newValue != null) {
                if(oldValue == null || !oldValue.equals(newValue)) {
                    UserDTO uDto = newValue;
                    dc.setCurrentUser(uDto);
                }
            }
        });
        }

    @FXML
    private void filter() {
        dc.filterUsers(txtFilter.getText(), cboType.getSelectionModel().getSelectedIndex());
    }

    @FXML
    public void deleteUser(){
        int index = userTable.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        dc.deleteUser();
    }

    @FXML
    public void newUser(){
        UserDTO newUser = new UserDTO();
        Date defaultBirthDay = Date.valueOf(LocalDate.of(1920, 1, 1));
        Date registrationDate = Date.valueOf(LocalDate.now());
        Address defaultAddress = new Address();
        defaultAddress.setCity("");
        defaultAddress.setCountry("");
        defaultAddress.setStreet("");
        defaultAddress.setNumber(1);
        defaultAddress.setZipCode(1000);
        defaultAddress.setBus("");

        Formula defaultFormula = new Formula();
        defaultFormula.setFormulaName("");
        newUser.setBirthday(defaultBirthDay);
        newUser.setRegistrationdate(registrationDate);
        newUser.setGrade(1);
        newUser.setType("Member");
        newUser.setAddressByAddressId(defaultAddress);
        dc.setCurrentUser(newUser);

    }
}
