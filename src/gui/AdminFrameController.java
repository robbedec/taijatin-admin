package gui;

import domain.DomainController;
import domain.Grade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AdminFrameController extends BorderPane {
    private DomainController dc;
    private OverviewPanelController overviewPanelController;
    private DetailPanelController detailPanelController;
    private ActivityOverviewPanelController activityOverviewPanelController;
    private ActivityDetailPanelController activityDetailPanelController;
    private OverzichtenPanelController overzichtenPanelController;

    @FXML
    private FlowPane fp;

    @FXML
    private VBox vbLeft, vbTop;

    @FXML
    private HBox hbTop;

    @FXML
    private Button btnLeden, btnActiviteiten, btnOverzichten, btnLesmateriaal;

    public AdminFrameController(DomainController dc) {
        this.dc = dc;

        overviewPanelController = new OverviewPanelController(this.dc, Arrays.asList(Types.values()), Arrays.asList(Grades.values()));
        //dc.addPropertyChangeListener(overviewPanelController);

        detailPanelController = new DetailPanelController(this.dc);
        dc.addPropertyChangeListener(detailPanelController);

        activityOverviewPanelController = new ActivityOverviewPanelController(this.dc, Arrays.asList(TypeOfActivity.values()));

        activityDetailPanelController = new ActivityDetailPanelController(this.dc);
        dc.addActivityPropertyChangeListener(activityDetailPanelController);

        overzichtenPanelController = new OverzichtenPanelController(this.dc, Arrays.asList(Overzichten.values()));

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
        clearScreens();
        this.vbLeft.getChildren().add(overviewPanelController);
        this.setCenter(detailPanelController);
    }

    public void showActiviteiten() {
        clearScreens();
        this.vbLeft.getChildren().add(activityOverviewPanelController);
        this.setCenter(activityDetailPanelController);
    }

    public void showOverzichten() {
        clearScreens();
        this.vbTop.getChildren().add(overzichtenPanelController);
    }

    public void showLesmateriaal() {

    }

    private void clearScreens() {
        this.vbLeft.getChildren().clear();
        this.vbTop.getChildren().clear();
        this.setCenter(null);
        this.vbTop.getChildren().add(hbTop);
    }
}
