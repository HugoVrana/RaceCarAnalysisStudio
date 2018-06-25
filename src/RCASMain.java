import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class RCASMain extends Application {

    public static void main(String[] args) {
        Application.launch(RCASMain.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(RCASMain.class.getResource("RCASMainView.fxml"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("RCASResources");
        fxmlLoader.setResources(resourceBundle);

        Pane mainPane = fxmlLoader.load();
        Scene mainScene = new Scene(mainPane, mainPane.getPrefWidth(), mainPane.getPrefHeight());
        primaryStage.centerOnScreen();
        primaryStage.setTitle(resourceBundle.getString("applicationTitle"));
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}