package main;

import ast.AndStatement;
import ast.LogicStatement;
import ast.Variable;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Controller Controller = new Controller();
        Controller.launchController(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
