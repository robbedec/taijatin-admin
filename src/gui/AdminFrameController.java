package gui;

import domain.DomainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class AdminFrameController extends BorderPane {
    private DomainController dc;
    private OverviewPanelController overviewPanelController;
    private DetailPanelController detailPanelController;
    private ActivityOverviewPanelController activityOverviewPanelController;
    private ActivityDetailPanelController activityDetailPanelController;

    @FXML
    private Button btnLeden, btnActiviteiten, btnOverzichten, btnLesmateriaal;

    public AdminFrameController(DomainController dc) {
        this.dc = dc;

        overviewPanelController = new OverviewPanelController(this.dc);
        //dc.addPropertyChangeListener(overviewPanelController);

        detailPanelController = new DetailPanelController(this.dc);
        dc.addPropertyChangeListener(detailPanelController);

        activityOverviewPanelController = new ActivityOverviewPanelController(this.dc);

        activityDetailPanelController = new ActivityDetailPanelController(this.dc);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminFrame.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //this.setLeft(overviewPanelController);
        //this.setCenter(detailPanelController);
        //this.setTop(detailPanelController);
    }

    public void showLeden() {
        this.setLeft(overviewPanelController);
        this.setCenter(detailPanelController);
    }

    public void showActiviteiten() {
        this.setLeft(activityOverviewPanelController);
        this.setCenter(activityDetailPanelController);
    }

    public void showOverzichten() {

    }

    public void showLesmateriaal() {

    }
}
