

package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Start extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        ProveController Controller = new ProveController();
        Controller.launchController(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
