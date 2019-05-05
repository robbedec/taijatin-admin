package gui;

import domain.DomainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
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
    private HBox hbTop;

    @FXML
    private Button btnLeden, btnActiviteiten, btnOverzichten, btnLesmateriaal;

    public AdminFrameController(DomainController dc) {
        this.dc = dc;

        overviewPanelController = new OverviewPanelController(this.dc, Arrays.asList(Types.values()));
        //dc.addPropertyChangeListener(overviewPanelController);

        detailPanelController = new DetailPanelController(this.dc);
        dc.addPropertyChangeListener(detailPanelController);

        activityOverviewPanelController = new ActivityOverviewPanelController(this.dc);

        activityDetailPanelController = new ActivityDetailPanelController(this.dc);
        dc.addActivityPropertyChangeListener(activityDetailPanelController);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminFrame.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        fp = new FlowPane();

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        showLeden();
    }

    public void showLeden() {
        this.vbLeft.getChildren().clear();
        this.vbLeft.getChildren().add(overviewPanelController);
        this.setCenter(detailPanelController);
    }

    public void showActiviteiten() {
        this.vbLeft.getChildren().clear();
        this.vbLeft.getChildren().add(activityOverviewPanelController);
        this.setCenter(activityDetailPanelController);
    }

    public void showOverzichten() {

    }

    public void showLesmateriaal() {

    }
}
