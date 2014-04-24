package main;

import ast.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author zl2511
 */
public class Prove {
    
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private List<LogicStatement> startStatements; 
    private LogicStatement goalStatement;
    
    @FXML
    private TextArea StartFomulars; 
    @FXML
    private TextField GoalFomular; 
    
    public Prove() throws IOException { 
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Prove.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
        
            scene = new Scene(parent);
        } catch (IOException e) {
        }
        
        
        
    }
    
    @FXML
    public void redirectprove(Stage stage, List<LogicStatement> sf, LogicStatement gf) { 
        
        this.startStatements = sf; 
        this.goalStatement = gf; 
        StartFomulars.setText(startStatements.toString());
        GoalFomular.setText(goalStatement.toString());
        stage.setScene(scene);
        stage.hide();
        stage.show();
        
    }
}
