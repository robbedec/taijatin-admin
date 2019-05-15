package gui;

import domain.DomainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class OverzichtenPanelController<T> extends VBox {

    private DomainController dc;
    private TableViewFactory factory;

    @FXML
    private ComboBox cboOverzichten;

    @FXML
    private TableView tableViewOverzicht;

    @FXML
    private FlowPane flowpane;

    @FXML
    private VBox vbTop;

    public OverzichtenPanelController(DomainController dc, List<T> enumInstances) {
        this.dc = dc;
        this.factory = new TableViewFactory(dc);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("OverzichtenPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        enumInstances.forEach(type -> cboOverzichten.getItems().add(type.toString()));

        cboOverzichten.getSelectionModel().selectedItemProperty().addListener(x -> {
            switch (cboOverzichten.getSelectionModel().getSelectedIndex()) {
                case 0:
                    tableViewOverzicht = factory.getBigActivityTableView();
                    clearScreen();
                    vbTop.getChildren().add(tableViewOverzicht);
                    break;
                case 1:
                    tableViewOverzicht = factory.getInschrijvingsTableView();
                    clearScreen();
                    vbTop.getChildren().add(tableViewOverzicht);
                    break;
                case 2:
                    tableViewOverzicht = factory.getClubKamptioenschapTableView();
                    clearScreen();
                    vbTop.getChildren().add(1, tableViewOverzicht);
                    break;
                case 3:
                    
                    break;
            }
        });
        cboOverzichten.getSelectionModel().select(0);
    }

    private void clearScreen() {
        this.vbTop.getChildren().clear();
        this.vbTop.getChildren().add(flowpane);
    }
}
