package main;

import domain.DomainController;
import gui.AdminFrameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StartUpGUI extends Application {
    public static void main(String[] args) {
        Application.launch(StartUpGUI.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new AdminFrameController(new DomainController()));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin paneel");
        primaryStage.setMaximized(true);
        primaryStage.setOnShown((WindowEvent t) -> {
            primaryStage.setMinWidth(primaryStage.getWidth());
            primaryStage.setMinHeight(primaryStage.getHeight());
        });
        primaryStage.show();
    }
}
