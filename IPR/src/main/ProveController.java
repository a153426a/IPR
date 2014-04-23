package main;


import ast.LogicStatement;
import ast.Symbol;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zl2511
 */
public class ProveController implements Initializable {
    
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private List<LogicStatement> startFormulas; 
    private LogicStatement goalFormula;

    @FXML
    public Button proveAnd, proveIFF, proveOr, proveImplies, proveNot, proveP, proveTrue, proveFalsity, proveThereexists, proveForall; 
    @FXML
    public Button proveCheck, proveCancel, proveStartButton;
    @FXML
    public Button proveButton0, proveButton1, proveButton2, proveButton3, proveButton4, proveButton5, proveButton6, proveButton7, proveButton8, proveButton9, proveButton10, proveButton11, proveButton12, proveButton13, proveButton14, proveButton15, proveButton16, proveButton17, proveButton18, proveButton19;
    @FXML
    public Button andI, andE, impliesI, impliesE, orI, orE, truthI, truthE, falsityI, falsityE, IFFI, IFFE, thereexistsI, thereexistE, forallI, forallE, notI, notE, notnot;
    @FXML
    public Label givenFormula0, givenFormula1, givenFormula2, givenFormula3, givenFormula4, givenFormula5, givenFormula6, givenFormula7, givenFormula8, givenFormula9, givenFormula10, givenFormula11, givenFormula12, givenFormula13, givenFormula14, givenFormula15, givenFormula16, givenFormula17, givenFormula18, givenFormula19; 
    @FXML
    public Label giveRule0, giveRule1, giveRule2, giveRule3, giveRule4, giveRule5, giveRule6, giveRule7, giveRule8, giveRule9, giveRule10, giveRule11, giveRule12, giveRule13, giveRule14, giveRule15, giveRule16, giveRule17, giveRule18, giveRule19;
    @FXML
    public Label proveGoalFormula, proveGoalRule; 
    @FXML
    public TextField proveFormula0, proveFormula1, proveFormula2, proveFormula3, proveFormula4, proveFormula5, proveFormula6, proveFormula7, proveFormula8, proveFormula9, proveFormula10, proveFormula11, proveFormula12, proveFormula13, proveFormula14, proveFormula15, proveFormula16, proveFormula17, proveFormula18, proveFormula19; 
    @FXML
    public TextField proveRule0, proveRule1, proveRule2, proveRule3, proveRule4, proveRule5, proveRule6, proveRule7, proveRule8, proveRule9, proveRule10, proveRule11, proveRule12, proveRule13, proveRule14, proveRule15, proveRule16, proveRule17, proveRule18, proveRule19;
    
    public TextField[][] proveTextfields=  new TextField[2][20]; 

    public TextField[] proveFormulaText = new TextField[20]; 

    public TextField[] proveRuleText = new TextField[20];  

    public int proveFocus = 0; 

    public int proveSubFocus = 0; 
    
    public ProveController() throws IOException { 
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("proveWindow.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
        
            scene = new Scene(parent);
        } catch (IOException e) {
                e.printStackTrace();
        }
        
    }
    
    public void redirectprove(Stage stage, List<LogicStatement> startFormulas, LogicStatement goalFormula) { 
        
        this.startFormulas = startFormulas; 
        this.goalFormula = goalFormula; 
        proveFormulaText[0].setText(startFormulas.get(0).toString());
        proveGoalFormula.setText(goalFormula.toString());
        
        stage.setScene(scene);
        stage.hide();
        stage.show();
        
    }
    
    @FXML 
    public void proveAndAction(ActionEvent event) {
        proveTextfields[proveFocus][proveSubFocus].setText(proveTextfields[proveFocus][proveSubFocus].getText() + Symbol.AND);
    }
    
    @FXML 
    public void proveIFFAction(ActionEvent event) {
        proveTextfields[proveFocus][proveSubFocus].setText(proveTextfields[proveFocus][proveSubFocus].getText() + Symbol.AND);
    }
    
    @FXML 
    public void proveOrAction(ActionEvent event) {
        proveTextfields[proveFocus][proveSubFocus].setText(proveTextfields[proveFocus][proveSubFocus].getText() + Symbol.OR);
    }
    
    @FXML 
    public void proveImpliesAction(ActionEvent event) {
        proveTextfields[proveFocus][proveSubFocus].setText(proveTextfields[proveFocus][proveSubFocus].getText() + Symbol.IMPLIES);
    }
    
    @FXML 
    public void proveNotAction(ActionEvent event) {
        proveTextfields[proveFocus][proveSubFocus].setText(proveTextfields[proveFocus][proveSubFocus].getText() + Symbol.NOT);
    }
    
    @FXML 
    public void provePredicateAction(ActionEvent event) {
        proveTextfields[proveFocus][proveSubFocus].setText(proveTextfields[proveFocus][proveSubFocus].getText() + Symbol.PREDICATE);
    }
    
    @FXML 
    public void proveTruthAction(ActionEvent event) {
        proveTextfields[proveFocus][proveSubFocus].setText(proveTextfields[proveFocus][proveSubFocus].getText() + Symbol.TRUTH);
    }
    
    @FXML 
    public void proveFalsityAction(ActionEvent event) {
        proveTextfields[proveFocus][proveSubFocus].setText(proveTextfields[proveFocus][proveSubFocus].getText() + Symbol.FALSITY);
    }
    
    @FXML 
    public void proveThereexistsAction(ActionEvent event) {
        proveTextfields[proveFocus][proveSubFocus].setText(proveTextfields[proveFocus][proveSubFocus].getText() + Symbol.THEREEXISTS);
    }
    
    @FXML 
    public void proveForallAction(ActionEvent event) {
        proveTextfields[proveFocus][proveSubFocus].setText(proveTextfields[proveFocus][proveSubFocus].getText() + Symbol.FORALL);
    }
    
    @FXML 
    public void applyAndI(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyAndE(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyImpliesI(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyImpliesE(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyOrl(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyOrE(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyTruthI(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyTruthE(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyFalsityI(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyFalsityE(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyIFFI(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyIFFE(ActionEvent event) {
        
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
        
    }
    
    @FXML 
    public void applyNotE(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyNotnot(ActionEvent event) {
        
    }
    
    @FXML 
    public void proveCheckAction(ActionEvent event) {
        
    }
    
    @FXML 
    public void proveCancelAction(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton0(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton1(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton2(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton3(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton4(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton5(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton6(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton7(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton8(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton9(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton10(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton11(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton12(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton13(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton14(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton15(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton16(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton17(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton18(ActionEvent event) {
        
    }
    
    @FXML 
    public void applyProveButton19(ActionEvent event) {
        
    }
    
    @FXML
    public void setProveFocus00() { 
        proveFocus = 0; 
        proveSubFocus = 0; 
    }
    
    @FXML
    public void setProveFocus01() { 
        proveFocus = 0; 
        proveSubFocus = 1; 
    }
    
    @FXML
    public void setProveFocus02() { 
        proveFocus = 0; 
        proveSubFocus = 2; 
    }
    
    @FXML
    public void setProveFocus03() { 
        proveFocus = 0; 
        proveSubFocus = 3; 
    }
    
    @FXML
    public void setProveFocus04() { 
        proveFocus = 0; 
        proveSubFocus = 4; 
    }
    
    @FXML
    public void setProveFocus05() { 
        proveFocus = 0; 
        proveSubFocus = 5; 
    }
    
    @FXML
    public void setProveFocus06() { 
        proveFocus = 0; 
        proveSubFocus = 6; 
    }
    
    @FXML
    public void setProveFocus07() { 
        proveFocus = 0; 
        proveSubFocus = 7; 
    }
    
    @FXML
    public void setProveFocus08() { 
        proveFocus = 0; 
        proveSubFocus = 8; 
    }
    
    @FXML
    public void setProveFocus09() { 
        proveFocus = 0; 
        proveSubFocus = 9; 
    }
    
    @FXML
    public void setProveFocus010() { 
        proveFocus = 0; 
        proveSubFocus = 10; 
    }
    
    @FXML
    public void setProveFocus011() { 
        proveFocus = 0; 
        proveSubFocus = 11; 
    }
    
    @FXML
    public void setProveFocus012() { 
        proveFocus = 0; 
        proveSubFocus = 12; 
    }
    
    @FXML
    public void setProveFocus013() { 
        proveFocus = 0; 
        proveSubFocus = 13; 
    }
    
    @FXML
    public void setProveFocus014() { 
        proveFocus = 0; 
        proveSubFocus = 14; 
    }
    
    @FXML
    public void setProveFocus015() { 
        proveFocus = 0; 
        proveSubFocus = 15; 
    }
    
    @FXML
    public void setProveFocus016() { 
        proveFocus = 0; 
        proveSubFocus = 16; 
    }
    
    @FXML
    public void setProveFocus017() { 
        proveFocus = 0; 
        proveSubFocus = 17; 
    }
    
    @FXML
    public void setProveFocus018() { 
        proveFocus = 0; 
        proveSubFocus = 18; 
    }
    
    @FXML
    public void setProveFocus019() { 
        proveFocus = 0; 
        proveSubFocus = 19; 
    }
    
    @FXML
    public void setProveFocus10() { 
        proveFocus = 1; 
        proveSubFocus = 0; 
    }
    
    @FXML
    public void setProveFocus11() { 
        proveFocus = 1; 
        proveSubFocus = 1; 
    }

    @FXML
    public void setProveFocus12() { 
        proveFocus = 1; 
        proveSubFocus = 2; 
    }
    
    @FXML
    public void setProveFocus13() { 
        proveFocus = 1; 
        proveSubFocus = 3; 
    }
    
    @FXML
    public void setProveFocus14() { 
        proveFocus = 1; 
        proveSubFocus = 4; 
    }
    
    @FXML
    public void setProveFocus15() { 
        proveFocus = 1; 
        proveSubFocus = 5; 
    }
    
    @FXML
    public void setProveFocus16() { 
        proveFocus = 1; 
        proveSubFocus = 6; 
    }
    
    @FXML
    public void setProveFocus17() { 
        proveFocus = 1; 
        proveSubFocus = 7; 
    }
    
    @FXML
    public void setProveFocus18() { 
        proveFocus = 1; 
        proveSubFocus = 8; 
    }
    
    @FXML
    public void setProveFocus19() { 
        proveFocus = 1; 
        proveSubFocus = 9; 
    }
    
    @FXML
    public void setProveFocus110() { 
        proveFocus = 1; 
        proveSubFocus = 10; 
    }
    
    @FXML
    public void setProveFocus111() { 
        proveFocus = 1; 
        proveSubFocus = 11; 
    }
    
    @FXML
    public void setProveFocus112() { 
        proveFocus = 1; 
        proveSubFocus = 12; 
    }
    
    @FXML
    public void setProveFocus113() { 
        proveFocus = 1; 
        proveSubFocus = 13; 
    }
    
    @FXML
    public void setProveFocus114() { 
        proveFocus = 1; 
        proveSubFocus = 14; 
    }
    
    @FXML
    public void setProveFocus115() { 
        proveFocus = 1; 
        proveSubFocus = 15; 
    }
    
    @FXML
    public void setProveFocus116() { 
        proveFocus = 1; 
        proveSubFocus = 16; 
    }
    
    @FXML
    public void setProveFocus117() { 
        proveFocus = 1; 
        proveSubFocus = 17; 
    }
    
    @FXML
    public void setProveFocus118() { 
        proveFocus = 1; 
        proveSubFocus = 18; 
    }
    
    @FXML
    public void setProveFocus119() { 
        proveFocus = 1; 
        proveSubFocus = 19; 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        proveFormulaText[0] = proveFormula0; 
        proveFormulaText[1] = proveFormula1; 
        proveFormulaText[2] = proveFormula2;
        proveFormulaText[3] = proveFormula3; 
        proveFormulaText[4] = proveFormula4; 
        proveFormulaText[5] = proveFormula5; 
        proveFormulaText[6] = proveFormula6; 
        proveFormulaText[7] = proveFormula7; 
        proveFormulaText[8] = proveFormula8; 
        proveFormulaText[9] = proveFormula9; 
        proveFormulaText[10] = proveFormula10; 
        proveFormulaText[11] = proveFormula11; 
        proveFormulaText[12] = proveFormula12;
        proveFormulaText[13] = proveFormula13; 
        proveFormulaText[14] = proveFormula14; 
        proveFormulaText[15] = proveFormula15; 
        proveFormulaText[16] = proveFormula16; 
        proveFormulaText[17] = proveFormula17; 
        proveFormulaText[18] = proveFormula18; 
        proveFormulaText[19] = proveFormula19;  
        proveRuleText[0] = proveRule0; 
        proveRuleText[1] = proveRule1; 
        proveRuleText[2] = proveRule2; 
        proveRuleText[3] = proveRule3; 
        proveRuleText[4] = proveRule4; 
        proveRuleText[5] = proveRule5; 
        proveRuleText[6] = proveRule6; 
        proveRuleText[7] = proveRule7; 
        proveRuleText[8] = proveRule8; 
        proveRuleText[9] = proveRule9; 
        proveRuleText[10] = proveRule10; 
        proveRuleText[11] = proveRule11; 
        proveRuleText[12] = proveRule12; 
        proveRuleText[13] = proveRule13; 
        proveRuleText[14] = proveRule14; 
        proveRuleText[15] = proveRule15; 
        proveRuleText[16] = proveRule16; 
        proveRuleText[17] = proveRule17; 
        proveRuleText[18] = proveRule18; 
        proveRuleText[19] = proveRule19; 
        proveTextfields[0] = proveFormulaText;
        proveTextfields[1] = proveRuleText;
    }
    
}
