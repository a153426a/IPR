package main;

import ast.*;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
import lexerAndParser.Lexer;
import lexerAndParser.parser;

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
    
    private List<boxStartingLine> boxes;
    private String[] boxColors;
    
    /*
    @FXML
    private TextArea StartFomulars; 
    
    @FXML
    private TextField GoalFomular; 
    */
    @FXML
    private GridPane provePane;
    @FXML
    private Button andI, andE, impliesI, impliesE, orI, orE, truthI, falsityI, falsityE, IFFI, IFFE, thereexistsI, thereexistE, forallI, forallE, notI, notE, notnot, ass, lemma, tick, createBox, createBoxTwo, createNewLine; 
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
        boxes = new ArrayList<boxStartingLine>();
        boxColors = new String[20];
        boxColors[0] = "-fx-border-color: red";
        boxColors[1] = "-fx-border-color: blue";
        boxColors[2] = "-fx-border-color: blueviolet";
        boxColors[3] = "-fx-border-color: black";
        boxColors[4] = "-fx-border-color: brown";
        boxColors[5] = "-fx-border-color: chartreuse";
        boxColors[6] = "-fx-border-color: coral";
        boxColors[7] = "-fx-border-color: darkorchid";
        boxColors[8] = "-fx-border-color: darkslategray";
        boxColors[9] = "-fx-border-color: deeppink";
        boxColors[10] = "-fx-border-color: forestgreen";
        boxColors[11] = "-fx-border-color: gold";
        boxColors[12] = "-fx-border-color: gray";
        boxColors[13] = "-fx-border-color: lightcoral";
        boxColors[14] = "-fx-border-color: mediumblue";
        boxColors[15] = "-fx-border-color: aqua";
        boxColors[16] = "-fx-border-color: yellow";
        boxColors[17] = "-fx-border-color: steelblue";
        boxColors[18] = "-fx-border-color: tan";
        boxColors[19] = "-fx-border-color: silver";
        
        
        
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
        
        //currentMaxLine++;
        //items.add(new ProveLine(currentMaxLine));
        proveView.setItems(items);
        provePane.add(starBox,1,0);
        provePane.add(goalLine, 1, 2);
        provePane.add(proveView, 1, 1);
        stage.setScene(scene);
        stage.hide();
        stage.show();
        
    }
    
    @FXML 
    public void checkButtonAction(ActionEvent event) throws Exception{ 
        
        boolean result = true; 
        //check args
        
        //check meaning 
        for (ProveLine p:items) { 
            System.out.println("Checking line number " + p.getNum() + ". ");
            if (!checkProveLine(p)) { 
                result = false; 
                System.out.println("Line number " + p.getNum() + " is wrong. ");
            } else { 
                System.out.println("Line number " + p.getNum() + " is correct. ");
            }
        }
        if(result && !stringToLS(items.get(items.size()-1).getFml().getText()).equalsTo(goalStatement)) { 
            System.out.println("You did well, carry on and get the result. ");
        } else if (result && stringToLS(items.get(items.size()-1).getFml().getText()).equalsTo(goalStatement)) { 
            System.out.println("Congratulations! You have got the solution! ");
        }
    }
    
    private boolean checkProveLine(ProveLine p) throws Exception{ 
        //check if empty
        String ruleName = p.getRule(); 
        if (p.getFml().getText().equals("")) { 
            System.out.println("The Line number " + p.getNum()+ " is empty. ");
            return false; 
        }
        //check if correct
        LogicStatement fml = stringToLS(p.getFml().getText());
        //if it has arguments 
        if(p.haveArguments()) { 
            //check if the given aruments is valid
            //get array of arguments
            LogicStatement[] argumentsStatement = new LogicStatement[p.getArguments().length];
            List<Integer> argumentLineNumber = new ArrayList<Integer>();
            for(int i = 0; i < p.getArguments().length; i++) { 
                argumentLineNumber.add(p.getArguments()[i]);
                argumentsStatement[i] = stringToLS(findLine(p.getArguments()[i]));
            } 
            if(!checkLegalArgs(argumentLineNumber, p.getLegalArgs())) { 
                System.out.println("The given lines is invalid to be the arguments for the current line(You can not use these given lines as argument. )");
                return false; 
            }
            
            
            //check if the rules is applied correctly
            switch(ruleName) {
                case "∧I": return checkAndI(fml, new AndStatement(argumentsStatement[0], argumentsStatement[1])); 
                case "∧E": return checkAndE(fml, argumentsStatement[0]);
                case "→E": return checkImpliesE(argumentsStatement[0], new ImpliesStatement(argumentsStatement[1], fml));
                case "∨I": return checkOrI(fml,argumentsStatement[0]); 
                case "⊥I": return checkFalsityI(fml, argumentsStatement[0], argumentsStatement[1]);
                case "⊥E": return checkFalsityE(fml, argumentsStatement[0]);
                case "↔I": return checkIFFI(fml, argumentsStatement[0], argumentsStatement[1]);
                case "↔E": return checkIFFE(fml, argumentsStatement[0], argumentsStatement[1]); 
                case "¬E": return checkNotE(fml, argumentsStatement[0], argumentsStatement[1]);
                case "¬¬": return checkNotNot(fml, argumentsStatement[0]);
                    //2 args, need a box
                case "→I": return checkImpliesI(p, p.getArguments()[0], p.getArguments()[1]);
                    //2 args, need a box
                case "¬I": return checkNotI(p, p.getArguments()[0], p.getArguments()[1]);
                    //5 args, need two boxes 
                case "∨E": 
                
                default: return false;  
            } 
        }        else { 
            switch(ruleName) {
                case "⊤I": return checkTruthI(fml);
                case "ass": return true;
                case "lemma": //need to ask
                case "tick": //need to ask
            }
        }
        return false;
    }
    
    private boolean checkLegalArgs(List<Integer> l1, List<Integer> l2) { 
        if(l2.containsAll(l1)) { 
            return true;
        } else { 
            return false; 
        }
    }
    
    private void updateLegalArgs(ProveLine pl) {
        int position = pl.getNum()-givenLineNum-1;
        List<ProveLine> below = new ArrayList<ProveLine>(); 
        for(int j = position; j<items.size()-1; j++) { 
            below.add(items.get(j));
        }
        if(checkIfFirstLine(pl) && !(pl instanceof boxStartingLine)) { 
            for(int i = 1; i < givenLineNum+1; i++) { 
                pl.addLegalArgs(i);
            }
            for(ProveLine plpl: below) { 
                plpl.addLegalArgs(position);
            }
        } else if(checkIfFirstLine(pl) && pl instanceof boxStartingLine) { 
            for(int i = 1; i < givenLineNum+1; i++) { 
                pl.addLegalArgs(i);
            }
        } else { 
            ProveLine upper = items.get(pl.getNum()-givenLineNum-2);
            if(pl instanceof boxStartingLine || pl instanceof boxClosingLine) { 
                for(int k:upper.getLegalArgs()) { 
                    pl.addLegalArgs(k);
                }
                pl.addLegalArgs(upper.getNum());
            } else if(pl.isInBox()) { 
                for(int k:upper.getLegalArgs()) { 
                    pl.addLegalArgs(k);
                }
                pl.addLegalArgs(upper.getNum());
                for(ProveLine plpl:findParentBox(pl).getLineInBox()) { 
                    if(below.contains(plpl)) { 
                        plpl.addLegalArgs(pl.getNum());
                    }
                }
            } else if(upper instanceof boxClosingLine) { 
                pl.addLegalArgs(upper.getNum());
                for(int k:upper.getLegalArgs()) { 
                    pl.addLegalArgs(k);
                }
                pl.addLegalArgs(((boxClosingLine) upper).getStartLine().getNum());
                for(ProveLine plpl: below) { 
                    plpl.addLegalArgs(position);
                }
            } else { 
                pl.addLegalArgs(upper.getNum());
                for(int k:upper.getLegalArgs()) { 
                    pl.addLegalArgs(k);
                }
                for(ProveLine plpl: below) { 
                    plpl.addLegalArgs(position);
                }
            }
        }
            
            
            
    } 
    
    private boolean checkIfFirstLine(ProveLine pl) { 
        if (pl.getNum()-givenLineNum-1==0) { 
            return true;
        } else { 
            return false; 
        }
    }
    
    //input Line number, return the given/prove line with that line number 
    private String findLine(int i) { 
        if(i<=givenLineNum) { 
            return ((GivenLine)starBox.getChildren().get(i-1)).getfml();
        } else { 
            return items.get(i-givenLineNum-1).getFml().getText();
        }
    }
    
    private LogicStatement stringToLS(String s) throws Exception { 
        
        LogicStatement ls = null; 
        InputStream is; 
        try {
            is = new ByteArrayInputStream(s.getBytes("UTF-8"));
            Lexer l = new Lexer(is);
            parser p = new parser(l);
            ls = (LogicStatement) p.parse().value;
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return ls;
        
    }
    
    
    private boolean checkAndI(LogicStatement s, AndStatement a) { 
        if(s instanceof AndStatement) { 
            
            if(((AndStatement)s).equalsTo(a)) { 
                return true;
            } else { 
                System.out.println("The given two lines can not produce this line by AndI. ");
                return false; 
            }
        } else { 
            System.out.println("AndI can not be applied on a none AndStatemnet. ");
            return false;
        }
    }
    
    private boolean checkAndE(LogicStatement s, LogicStatement a){ 
        if(a instanceof AndStatement) { 
            if(((AndStatement) a).nestedStatementLeft.equalsTo(s) || ((AndStatement) a).nestedStatementRight.equalsTo(s)) { 
                return true;
            } else { 
                System.out.println("The given line can not produce ths line by AndI. ");
                return false; 
            }
        } else { 
            System.out.println("The given line is not an AndStatement. ");
            return false;
        }
    }
    
    private boolean checkImpliesE(LogicStatement s, ImpliesStatement a) { 
        if(s instanceof ImpliesStatement) { 
            if(((ImpliesStatement)s).equalsTo(a)) { 
                 return true;
            } else { 
                System.out.println("The given line can not produce ths line by ImpliesI. ");
                return false; 
            }
        } else { 
            System.out.println("The given line is not an ImpliesStatement. ");
            return false;
        }
    }
    
    private boolean checkOrI(LogicStatement s, LogicStatement a) { 
        if(s instanceof OrStatement) { 
            if(((OrStatement) s).nestedStatementLeft.equalsTo(a) || ((OrStatement) s).nestedStatementRight.equalsTo(a)) { 
                return true;
            } else { 
                System.out.println("The given line is not part of this OrStatement. ");
                return false; 
            }
        } else { 
            System.out.println("The given line is not an OrStatement. ");
            return false;
        }
    }
    
    private boolean checkTruthI(LogicStatement s) { 
        if (s instanceof Truth) { 
            return true;
        } else { 
            System.out.println("Truth should be introduced. ");
            return false; 
        }
    }
    
    private boolean checkFalsityI(LogicStatement s, LogicStatement a1, LogicStatement a2) { 
        if (s instanceof Falsity) {
            if (a2 instanceof NotStatement) {
                if (new NotStatement(a1).equalsTo(((NotStatement) a2))) { 
                    return true;
                } else { 
                    System.out.println("The second given line should be a NotStatement created by first given line. ");
                    return false; 
                }
            } else { 
                System.out.println("The second given line should be a NotStatement. ");
                return false; 
            }
        } else { 
            System.out.println("Falsity should be introduced. ");
            return false; 
        }
    }
    
    private boolean checkFalsityE(LogicStatement s, LogicStatement a) { 
        if (a instanceof Falsity) { 
            return true;
        } else { 
            System.out.println("The givne line should be falsity. ");
            return false; 
        }
    }
    
    private boolean checkIFFI(LogicStatement s, LogicStatement a1, LogicStatement a2) { 
        if(s instanceof IFFStatement) { 
            if(a1 instanceof ImpliesStatement) {
                if(a2 instanceof ImpliesStatement) { 
                    if (((ImpliesStatement) a1).nestedStatementLeft.equalsTo(((ImpliesStatement) a2).nestedStatementRight) 
                        &&((ImpliesStatement) a1).nestedStatementRight.equalsTo(((ImpliesStatement) a2).nestedStatementLeft)) { 
                        if(((IFFStatement) s).nestedStatementLeft.equalsTo(((ImpliesStatement) a1).nestedStatementLeft) 
                            && ((IFFStatement) s).nestedStatementRight.equalsTo(((ImpliesStatement) a1).nestedStatementRight)) { 
                            return true;
                        } else { 
                            System.out.println("The given two lines can not produce this formula by IFFI. ");
                            return false; 
                        } 
                    } else { 
                        System.out.println("The left part of the first given line should be same as the right part of the right part of the second given line and vise versa. ");
                        return false; 
                    }
                } else { 
                    System.out.println("The second given line should be a ImpliesStatement. ");
                    return false; 
                }
            } else { 
                System.out.println("The first given line should be a ImpliesStatement. ");
                return false; 
            }
        } else { 
            System.out.println("This given line should be a IFFStatement. ");
            return false; 
        }
    }
    
    private boolean checkIFFE(LogicStatement s, LogicStatement a1, LogicStatement a2) { 
        if (a1 instanceof IFFStatement) { 
            if (((IFFStatement) a1).nestedStatementLeft.equalsTo(a2) || ((IFFStatement) a1).nestedStatementRight.equalsTo(a2)) { 
                if(((IFFStatement) a1).nestedStatementLeft.equalsTo(a2) && ((IFFStatement) a1).nestedStatementRight.equalsTo(s)) { 
                    return true;
                } else if(((IFFStatement) a1).nestedStatementLeft.equalsTo(s) && ((IFFStatement) a1).nestedStatementRight.equalsTo(a2)) { 
                    return true; 
                } else { 
                    System.out.println("The second given line should not be on the same side with this line");
                    return false; 
                }
            } else { 
                System.out.println("The second given line should be a part of the first given line. ");
                return false;
            }
        } else { 
            System.out.println("The first given line should be a IFFStatement. ");
            return false;
        }
    }
    
    private boolean checkNotE(LogicStatement s, LogicStatement a1, LogicStatement a2) { 
        if (s instanceof Falsity) { 
            if (a1 instanceof NotStatement) { 
                if (new NotStatement(a2).equalsTo(a1)) { 
                    return true;
                } else { 
                    System.out.println("The first given line should be a NotStatement produced by the second given line. ");
                    return false; 
                }
            } else { 
                System.out.println("The first given line should be a NotStatement. ");
                return false;
            }
        } else { 
            System.out.println("This line should be falsity. ");
            return false; 
        }
    }
    
    private boolean checkNotNot(LogicStatement s, LogicStatement a) { 
        if (a instanceof NotStatement) { 
            if (((NotStatement) a).nestedStatement instanceof NotStatement) { 
                if (((NotStatement) ((NotStatement) a).nestedStatement).nestedStatement.equalsTo(s)) { 
                    return true;
                } else { 
                    System.out.println("This line should be a NotNotStatement produced by the first given line. ");
                    return false; 
                }
            } else { 
                System.out.println("The given line should be a NotNotStatement. ");
                return false; 
            }
        } else { 
            System.out.println("The given line should be a NotNotStatement. ");
            return false; 
        }
    }
    
    private boolean checkImpliesI(ProveLine p, int i1, int i2) throws Exception { 
        if(i1 > givenLineNum && i2 >givenLineNum) { 
            ProveLine p1 = items.get(i1-givenLineNum-1); 
            ProveLine p2 = items.get(i2-givenLineNum-1);
            if(p.getNum()-1 == i2 &&  p2 instanceof boxClosingLine && p1 instanceof boxStartingLine 
                    && ((boxClosingLine) p2).getStartLine().equals((boxStartingLine)p1) && p1.getRule().equals("ass")) { 
                LogicStatement l1 = stringToLS(findLine(i1)); 
                LogicStatement l2 = stringToLS(findLine(i2)); 
                LogicStatement pp = stringToLS(p.getFml().getText());
                if(pp instanceof ImpliesStatement) { 
                    if(((ImpliesStatement) pp).nestedStatementLeft.equalsTo(l1) && ((ImpliesStatement) pp).nestedStatementRight.equalsTo(l2)) { 
                        return true;
                    } else { 
                        return false;
                    }
                } else { 
                    return false;
                }
            } else { 
                return false;
            } 
        } else { 
            return false;
        }
    }
    
    private boolean checkNotI(ProveLine p, int i1, int i2) throws Exception { 
        if(i1 > givenLineNum && i2 >givenLineNum) { 
            ProveLine p1 = items.get(i1-givenLineNum-1); 
            ProveLine p2 = items.get(i2-givenLineNum-1);
            if(p.getNum()-1 == i2 &&  p2 instanceof boxClosingLine && p1 instanceof boxStartingLine 
                    && ((boxClosingLine) p2).getStartLine().equals((boxStartingLine)p1) && p1.getRule().equals("ass")) { 
                LogicStatement l1 = stringToLS(findLine(i1)); 
                LogicStatement l2 = stringToLS(findLine(i2)); 
                LogicStatement pp = stringToLS(p.getFml().getText());
                if(pp instanceof NotStatement) { 
                    if(l2 instanceof Falsity && pp.equalsTo(new NotStatement(l1))) { 
                        return true;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }
    
    @FXML 
    public void cancelButtonAction(ActionEvent event) { 
        
        
        
    }
    
    private int getCurrentFocus() { 
        int idx = proveView.getSelectionModel().getSelectedIndex(); 
        return idx;
    }
    
    @FXML 
    public void createNewLineButton(ActionEvent event) {
        currentMaxLine++;
        
        int selectedLine = proveView.getSelectionModel().getSelectedIndex(); 
        if(selectedLine == -1) { 
            ProveLine pl = new ProveLine(currentMaxLine);
        
            items.add(currentMaxLine-givenLineNum-1, pl);
        } else { 
            ProveLine pl = new ProveLine(selectedLine+1+givenLineNum+1); 
            items.add(selectedLine+1, pl);
            for(int i = selectedLine+2; i<items.size(); i++) { 
                items.get(i).reAssignNum(i+givenLineNum+1);
            }
            assignBox(pl, items.get(selectedLine));
        }
    }
    
    private void assignBox(ProveLine pl, ProveLine upper) { 
        if(upper instanceof boxStartingLine || upper.isInBox()) { 
            pl.setInBox();
            findParentBox(pl).addLineInBox(pl);
            for(boxStartingLine bs: findParentBox(pl).getParentBox()) { 
                bs.addLineInBox(pl);
            }
            pl.setStyle("-fx-border-color: black");
        }
    }
    
    private boxStartingLine findParentBox(ProveLine pl) { 
        ProveLine upper = items.get(pl.getNum()-givenLineNum-2);
        if(!(upper instanceof boxStartingLine)) { 
            return findParentBox(upper);
        } else { 
            return (boxStartingLine) upper;
        }
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
    public void applyTick(ActionEvent event) {
        applyRule("yes",0);
    }
    
    @FXML 
    public void applyCreateBox(ActionEvent event) {
        currentMaxLine++;
        int selectedLine = proveView.getSelectionModel().getSelectedIndex(); 
        
        if(selectedLine == -1) { 
            
            boxStartingLine bs = new boxStartingLine(currentMaxLine);
            items.add(currentMaxLine-givenLineNum-1, bs);
            
            currentMaxLine++;
            boxClosingLine bc = new boxClosingLine(currentMaxLine);
            
            bs.setEndLine(bc);
            bc.setStartLine(bs);
            boxes.add(bs);
            applyColor(bs);
            
            items.add(currentMaxLine-givenLineNum-1, bc);
            
        } else { 
            
            boxStartingLine bs = new boxStartingLine(selectedLine+1+givenLineNum+1); 
            items.add(selectedLine+1, bs); 
            currentMaxLine++;
            boxClosingLine bc = new boxClosingLine(selectedLine+1+givenLineNum+2);
            
            bs.setEndLine(bc);
            bc.setStartLine(bs);
            applyColor(bs);
            boxes.add(bs);
            
            items.add(selectedLine+2, bc);
            for(int i = selectedLine+3; i<items.size(); i++) { 
                items.get(i).reAssignNum(i+givenLineNum+1);
            }
            
            assignBoxToBox(bs, bc, items.get(selectedLine));
            
        }
        
    }
    
    private void assignBoxToBox(boxStartingLine bs, boxClosingLine bc, ProveLine upper) { 
        if(upper instanceof boxStartingLine || upper.isInBox()) { 
            bs.setInBox();
            bc.setInBox();
            //items.get(pl.getNum()-givenLineNum-2);
            findParentBox(bs).addSubBoxes(bs);
            bs.addParentBox(findParentBox(bs));
            findParentBox(bs).addLineInBox(bs);
            findParentBox(bs).addLineInBox(bc);
            for(boxStartingLine bsl:findParentBox(bs).getParentBox()) { 
                bs.addParentBox(bsl);
                bsl.addLineInBox(bs);
                bsl.addLineInBox(bc);
            }
        }
    }
    
    private void applyColor(boxStartingLine bs) { 
        bs.setStyle(boxColors[boxes.size()]);
        bs.getEndLine().setStyle(boxColors[boxes.size()]);
    }
    
    @FXML 
    public void applyCreateBoxTwo(ActionEvent event) {
        //TODO
    }
    
    
    @FXML 
    public void andAction(ActionEvent event) {
        int i = getCurrentFocus();
        if(i==-1) { 
            //items.get(0).getFml().insertText(0, Symbol.AND.toString());
        } else { 
            items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.AND.toString());
        }
        //items.get(i).getCurrentTextField().setText(items.get(i).getCurrentTextField().getText() + Symbol.AND);
        
    }
    
    @FXML 
    public void IFFAction(ActionEvent event) {
        int i = getCurrentFocus();
        if(i==-1) { 
            //items.get(0).getFml().insertText(0, Symbol.IFF.toString());
        } else { 
            items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.IFF.toString());
        }
    }
    
    @FXML 
    public void orAction(ActionEvent event) {
        int i = getCurrentFocus();
        if(i==-1) { 
            //items.get(0).getFml().insertText(0, Symbol.OR.toString());
        } else { 
            items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.OR.toString());
        }
    }
    
    @FXML 
    public void impliesAction(ActionEvent event) {
        int i = getCurrentFocus();
        if(i==-1) { 
            //items.get(0).getFml().insertText(0, Symbol.IMPLIES.toString());
        } else { 
            items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.IMPLIES.toString());
        }
    }
    
    @FXML 
    public void notAction(ActionEvent event) {
        int i = getCurrentFocus();
        if(i==-1) { 
            //items.get(0).getFml().insertText(0, Symbol.NOT.toString());
        } else { 
            items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.NOT.toString());
        }
    }
    
    @FXML 
    public void truthAction(ActionEvent event) {
        int i = getCurrentFocus();
        if(i==-1) { 
            //items.get(0).getFml().insertText(0, Symbol.TRUTH.toString());
        } else { 
            items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.TRUTH.toString());
        }
    }
    
    @FXML 
    public void falsityAction(ActionEvent event) {
        int i = getCurrentFocus();
        if(i==-1) { 
            //items.get(0).getFml().insertText(0, Symbol.FALSITY.toString());
        } else { 
            items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.FALSITY.toString());
        }
    }
    
    @FXML 
    public void thereexistsAction(ActionEvent event) {
        int i = getCurrentFocus();
        if(i==-1) { 
            //items.get(0).getFml().insertText(0, Symbol.THEREEXISTS.toString());
        } else { 
            items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.THEREEXISTS.toString());
        }
    }
    
    @FXML 
    public void forallAction(ActionEvent event) {
        int i = getCurrentFocus();
        if(i==-1) { 
            //items.get(0).getFml().insertText(0, Symbol.FORALL.toString());
        } else { 
            items.get(i).getCurrentTextField().insertText(items.get(i).getCaretIndex(), Symbol.FORALL.toString());
        }
    }
    
}
