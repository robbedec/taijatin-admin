package gui;

import domain.DomainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.concurrent.Flow;

public class AdminFrameController extends BorderPane {
    private DomainController dc;
    private OverviewPanelController overviewPanelController;
    private DetailPanelController detailPanelController;
    private ActivityOverviewPanelController activityOverviewPanelController;
    private ActivityDetailPanelController activityDetailPanelController;
    private FlowPane fp;

    @FXML
    private VBox vbLeft;

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

        fp = new FlowPane();

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
        this.vbLeft.getChildren().add(overviewPanelController);
        //this.setLeft(overviewPanelController);
        //fp.getChildren().add(overviewPanelController);
        //BorderPane.setAlignment(detailPanelController, Pos.TOP_RIGHT);
        this.setCenter(detailPanelController);

        //this.setCenter(detailPanelController);
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
