package gui;

import domain.DomainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.List;

public class OverzichtenPanelController<T> extends FlowPane {

    private DomainController dc;
    private TableViewFactory factory;

    @FXML
    private ComboBox cboOverzichten;

    @FXML
    private TableView tableViewOverzicht;

    @FXML
    private FlowPane flowpane;

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
        cboOverzichten.getSelectionModel().select(0);

        cboOverzichten.getSelectionModel().selectedItemProperty().addListener(x -> {
            switch (cboOverzichten.getSelectionModel().getSelectedIndex()) {
                case 0:
                    System.out.println("test 0");
                    //tableViewOverzicht = factory.getClubKamptioenschapTableView();
                    break;
                case 1:
                    System.out.println("Inschrijvingen");
                    flowpane.getChildren().add(1, factory.getInschrijvingsTableView());
                    break;
                case 2:
                    System.out.println("Klubkampioenschap");
                    flowpane.getChildren().add(1, factory.getClubKamptioenschapTableView());
                    break;
            }
            //this.flowpane.getChildren().add(2, tableViewOverzicht);
        });
    }
}
