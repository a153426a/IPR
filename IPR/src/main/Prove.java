package main;

import ast.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private int currentMaxLine = 0;
    
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
    private Button andI, andE, impliesI, impliesE, orI, orE, truthI, falsityI, falsityE, IFFI, IFFE, thereexistsI, thereexistE, forallI, forallE, notI, notE, notnot, ass, lemma, correct, createBox, createBoxTwo, createNewLine; 
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
            currentMaxLine++;
            GivenLine gl = new GivenLine(currentMaxLine, l.toString(), "Given"); 
            starBox.getChildren().add(gl); 
            
            
        }
        
        goalLine = new GivenLine(0,gf.toString(), "");
        
        currentMaxLine++;
        items.add(new ProveLine(currentMaxLine));
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
    
    private int getCurrentFocus() { 
        int idx = proveView.getSelectionModel().getSelectedIndex(); 
        if (idx == -1) { 
            return 0; 
        } else { 
            return idx;
        }
    }
    
    @FXML 
    public void createNewLineButton(ActionEvent event) {
        currentMaxLine++;
        ProveLine pl = new ProveLine(currentMaxLine);
        
        items.add(currentMaxLine-givenLineNum-1, pl);
        proveView.setItems(items);
    }
    public void applyRule(String s, int i) { 
        int idx = getCurrentFocus();
        if (idx != -1) {
            if(!items.get(idx).getRuled()){
                items.get(idx).setRule(s, i);
                items.get(idx).setRuled();
            } else { 
                items.get(idx).removeRule();
                items.get(idx).setRule(s, i);
                items.get(idx).setRuled();
            }
        } 
    }
    
    @FXML 
    public void applyAndI(ActionEvent event) {
        applyRule("∧I",2);
    }

    
    @FXML 
    public void applyAndE(ActionEvent event) {
        applyRule("∧E",1);
    }
    
    @FXML 
    public void applyImpliesI(ActionEvent event) {
        //need a box
        applyRule("→I",2);
    }
    
    @FXML 
    public void applyImpliesE(ActionEvent event) {
        applyRule("→E",2);
    }
    
    @FXML 
    public void applyOrl(ActionEvent event) {
        applyRule("∨I",1);
    }
    
    @FXML 
    public void applyOrE(ActionEvent event) {
        //need 2 boxes 
        applyRule("∨E",5);
    }
    
    @FXML 
    public void applyTruthI(ActionEvent event) {
        applyRule("⊤I",0);
    }
    
    @FXML 
    public void applyFalsityI(ActionEvent event) {
        applyRule("⊥I",2);
    }
    
    @FXML 
    public void applyFalsityE(ActionEvent event) {
        applyRule("⊥E",1);
    }
    
    @FXML 
    public void applyIFFI(ActionEvent event) {
        applyRule("↔I",2);
    }
    
    @FXML 
    public void applyIFFE(ActionEvent event) {
        applyRule("↔E",2);
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
        applyRule("¬I",2);
    }
    
    @FXML 
    public void applyNotE(ActionEvent event) {
        applyRule("¬E",2);
    }
    
    @FXML 
    public void applyNotnot(ActionEvent event) {
        applyRule("¬¬",1);
    }
    
    @FXML 
    public void applyAss(ActionEvent event) {
        applyRule("ass",0);
    }
    
    @FXML 
    public void applyLemma(ActionEvent event) {
        applyRule("lemma",0);
    }
    
    @FXML 
    public void applyCorrect(ActionEvent event) {
        applyRule("yes",0);
    }
    
    @FXML 
    public void applyCreateBox(ActionEvent event) {
        //TODO
    }
    
    @FXML 
    public void applyCreateBoxTwo(ActionEvent event) {
        //TODO
    }
    
    
    @FXML 
    public void andAction(ActionEvent event) {
        int i = getCurrentFocus();
        //items.get(i).getCurrentTextField().setText(items.get(i).getCurrentTextField().getText() + Symbol.AND);
        items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.AND.toString());
    }
    
    @FXML 
    public void IFFAction(ActionEvent event) {
        int i = getCurrentFocus();
        items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.IFF.toString());
    }
    
    @FXML 
    public void orAction(ActionEvent event) {
        int i = getCurrentFocus();
        items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.OR.toString());
    }
    
    @FXML 
    public void impliesAction(ActionEvent event) {
        int i = getCurrentFocus();
        items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.IMPLIES.toString());
    }
    
    @FXML 
    public void notAction(ActionEvent event) {
        int i = getCurrentFocus();
        items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.NOT.toString());
    }
    
    @FXML 
    public void truthAction(ActionEvent event) {
        int i = getCurrentFocus();
        items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.TRUTH.toString());
    }
    
    @FXML 
    public void falsityAction(ActionEvent event) {
        int i = getCurrentFocus();
        items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.FALSITY.toString());
    }
    
    @FXML 
    public void thereexistsAction(ActionEvent event) {
        int i = getCurrentFocus();
        items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.THEREEXISTS.toString());
    }
    
    @FXML 
    public void forallAction(ActionEvent event) {
        int i = getCurrentFocus();
        items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.FORALL.toString());
    }
    
}
