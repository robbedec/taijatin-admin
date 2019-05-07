package gui;

import domain.DomainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class OverzichtenPanelController extends FlowPane {

    private DomainController dc;

    public OverzichtenPanelController(DomainController dc) {
        this.dc = dc;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("OverzichtenPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
