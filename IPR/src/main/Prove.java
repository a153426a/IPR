package main;

import ast.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private int givenLineNum = 0; 
    private int currentLine = 0;
    
    private VBox starBox;
    private ListView<ProveLine> proveView;
    private ObservableList<ProveLine> items;
    private GivenLine goalLine;
    /*
    @FXML
    private TextArea StartFomulars; 
    
    @FXML
    private TextField GoalFomular; 
    */
    @FXML
    private GridPane provePane;
    @FXML
    private Button andI, andE, impliesI, impliesE, orI, orE, truthI, falsityI, falsityE, IFFI, IFFE, thereexistsI, thereexistE, forallI, forallE, notI, notE, notnot, ass, lemma, correct, createBox, leaveBox; 
    @FXML 
    private Button checkButton, cancelButton;
    @FXML
    private Button andButton, IFFButton, orButton, impliesButton, notButton, truthButton, falsityButton, thereexistsButton, forallButton; 
    
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
        proveView = new ListView<ProveLine>();
        items = observableArrayList();
        
        //StartFomulars.setText(startStatements.toString());
        //GoalFomular.setText(goalStatement.toString());
        
        starBox = new VBox();
        for (LogicStatement l:sf) { 
            givenLineNum++;
            currentLine++;
            GivenLine gl = new GivenLine(currentLine, l.toString(), "Given"); 
            starBox.getChildren().add(gl); 
            
            
        }
        
        goalLine = new GivenLine(0,gf.toString(), "");
        
        currentLine++;
        items.add(new ProveLine(currentLine));
        proveView.setItems(items);
        provePane.add(starBox,1,0);
        provePane.add(goalLine, 1, 2);
        provePane.add(proveView, 1, 1);
        stage.setScene(scene);
        stage.hide();
        stage.show();
        
    }
    
    @FXML 
    public void checkButtonAction(ActionEvent event) { 
        
        
        
    }
    
    @FXML 
    public void cancelButtonAction(ActionEvent event) { 
        
        
        
    }
    
    @FXML 
    public void applyAndI(ActionEvent event) {
        currentLine++;
        items.get(items.size()-1).setRule("∧I", 2);
        
        items.add(currentLine-givenLineNum-1, new ProveLine(currentLine));
        proveView.setItems(items);
    }

    
    @FXML 
    public void applyAndE(ActionEvent event) {
        currentLine++;
        items.get(items.size()-1).setRule("∧E", 1);
        
        items.add(currentLine-givenLineNum-1, new ProveLine(currentLine));
        proveView.setItems(items);
    }
    
    @FXML 
    public void applyImpliesI(ActionEvent event) {
        //need a box
        currentLine++;
        items.get(items.size()-1).setRule("→I", 2);
        
        items.add(currentLine-givenLineNum-1, new ProveLine(currentLine));
        proveView.setItems(items);
    }
    
    @FXML 
    public void applyImpliesE(ActionEvent event) {
        currentLine++;
        items.get(items.size()-1).setRule("→E", 2);
        
        items.add(currentLine-givenLineNum-1, new ProveLine(currentLine));
        proveView.setItems(items);
    }
    
    @FXML 
    public void applyOrl(ActionEvent event) {
        currentLine++;
        items.get(items.size()-1).setRule("∨I", 1);
        
        items.add(currentLine-givenLineNum-1, new ProveLine(currentLine));
        proveView.setItems(items);
    }
    
    @FXML 
    public void applyOrE(ActionEvent event) {
        //need 2 boxes 
        currentLine++;
        items.get(items.size()-1).setRule("∨E", 5);
        
        items.add(currentLine-givenLineNum-1, new ProveLine(currentLine));
        proveView.setItems(items);
    }
    
    @FXML 
    public void applyTruthI(ActionEvent event) {
        currentLine++;
        items.get(items.size()-1).setRule("⊤I", 0);
        
        items.add(currentLine-givenLineNum-1, new ProveLine(currentLine));
        proveView.setItems(items);
    }
    
    @FXML 
    public void applyFalsityI(ActionEvent event) {
        currentLine++;
        items.get(items.size()-1).setRule("⊥I", 2);
        
        items.add(currentLine-givenLineNum-1, new ProveLine(currentLine));
        proveView.setItems(items);
    }
    
    @FXML 
    public void applyFalsityE(ActionEvent event) {
        currentLine++;
        items.get(items.size()-1).setRule("⊥E", 1);
        
        items.add(currentLine-givenLineNum-1, new ProveLine(currentLine));
        proveView.setItems(items);
    }
    
    @FXML 
    public void applyIFFI(ActionEvent event) {
        currentLine++;
        items.get(items.size()-1).setRule("↔I", 2);
        
        items.add(currentLine-givenLineNum-1, new ProveLine(currentLine));
        proveView.setItems(items);
    }
    
    @FXML 
    public void applyIFFE(ActionEvent event) {
        currentLine++;
        items.get(items.size()-1).setRule("↔E", 2);
        
        items.add(currentLine-givenLineNum-1, new ProveLine(currentLine));
        proveView.setItems(items);
    }
    
    @FXML 
    public void applyThereexistsI(ActionEvent event) {
    }
    
    @FXML 
    public void applyThereexistE(ActionEvent event) {
    }
    
    @FXML 
    public void applyForallI(ActionEvent event) {
    }
    
    @FXML 
    public void applyForallE(ActionEvent event) {
    }
    
    @FXML 
    public void applyNotI(ActionEvent event) {
        //need a box
        currentLine++;
        items.get(items.size()-1).setRule("¬I", 2);
        
        items.add(currentLine-givenLineNum-1, new ProveLine(currentLine));
        proveView.setItems(items);
    }
    
    @FXML 
    public void applyNotE(ActionEvent event) {
        currentLine++;
        items.get(items.size()-1).setRule("¬E", 2);
        
        items.add(currentLine-givenLineNum-1, new ProveLine(currentLine));
        proveView.setItems(items);
    }
    
    @FXML 
    public void applyNotnot(ActionEvent event) {
        currentLine++;
        items.get(items.size()-1).setRule("¬¬", 1);
        
        items.add(currentLine-givenLineNum-1, new ProveLine(currentLine));
        proveView.setItems(items);
    }
    
    @FXML 
    public void applyAss(ActionEvent event) {
        currentLine++;
        items.get(items.size()-1).setRule("ass", 0);
        
        items.add(currentLine-givenLineNum-1, new ProveLine(currentLine));
        proveView.setItems(items);
    }
    
    @FXML 
    public void applyLemma(ActionEvent event) {
        currentLine++;
        items.get(items.size()-1).setRule("lemma", 0);
        
        items.add(currentLine-givenLineNum-1, new ProveLine(currentLine));
        proveView.setItems(items);
    }
    
    @FXML 
    public void applyCorrect(ActionEvent event) {
        currentLine++;
        items.get(items.size()-1).setRule("yes", 0);
        
        items.add(currentLine-givenLineNum-1, new ProveLine(currentLine));
        proveView.setItems(items);
    }
    
    @FXML 
    public void applyCreateBox(ActionEvent event) {
        //TODO
    }
    
    @FXML 
    public void applyLeaveBox(ActionEvent event) {
        //TODO
    }
    
    
    @FXML 
    public void andAction(ActionEvent event) {
        System.out.println("1");
    }
    
    @FXML 
    public void IFFAction(ActionEvent event) {
        System.out.println("1");
    }
    
    @FXML 
    public void orAction(ActionEvent event) {
        System.out.println("1");
    }
    
    @FXML 
    public void impliesAction(ActionEvent event) {
        System.out.println("1");
    }
    
    @FXML 
    public void notAction(ActionEvent event) {
       System.out.println("1");
    }
    
    @FXML 
    public void truthAction(ActionEvent event) {
        System.out.println("1");
    }
    
    @FXML 
    public void falsityAction(ActionEvent event) {
        System.out.println("1");
    }
    
    @FXML 
    public void thereexistsAction(ActionEvent event) {
        System.out.println("1");
    }
    
    @FXML 
    public void forallAction(ActionEvent event) {
       System.out.println("1");
    }
    
}
