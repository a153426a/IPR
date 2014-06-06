package main;

import ast.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
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
    private Stage problemStage;
    private List<LogicStatement> startStatements; 
    private LogicStatement goalStatement;
    private int givenLineNum = 0; 
    private int currentMaxLine = 0;
    
    private VBox starBox;
    private ListView<ProveLine> proveView;
    private ObservableList<ProveLine> items;
    private GivenLine goalLine;
    
    private List<boxStartingLine> boxes;

    private ObservableList<HBox> problemHBox;
    
    private File currentFile;
    
    /*
    @FXML
    private TextArea StartFomulars; 
    
    @FXML
    private TextField GoalFomular; 
    */
    @FXML
    private GridPane provePane;
    @FXML
    private Button andI, andE, impliesI, impliesE, orI, orE, truthI, falsityI, falsityE, IFFI, IFFE, thereexistsI, thereexistE, forallI, forallE, notI, notE, notnot, ass, lemma, tick, createBox, createBoxTwo, createNewLine, PC; 
    @FXML 
    private Button checkButton, cancelButton;
    @FXML
    private Button andButton, IFFButton, orButton, impliesButton, notButton, truthButton, falsityButton, thereexistsButton, forallButton; 
    @FXML 
    private ListView<HBox> problemList;
    @FXML 
    private Button deleteButton;
    @FXML 
    private GridPane outsidePane;
    @FXML 
    private MenuBar menuBar;
    @FXML 
    private Menu menuFile, menuView, menuHelp;
    @FXML 
    private MenuItem menuItemNew, menuItemOpen, menuItemSave, menuItemSaveAs, menuItemImport, menuItemClose, menuItemClearBracket, menuItemAddBracket, menuItemAbout; 
    @FXML 
    private GridPane ruleBar;
    @FXML 
    private Button MoveUp, MoveDown;
    
    
    public Prove() throws IOException { 
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Prove.fxml"));
        fxmlLoader.setController(this);
            try {
                parent = (Parent) fxmlLoader.load();

                scene = new Scene(parent);
            } catch (IOException e) {
        }
        
        currentFile = null;
        menuItemSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
        
        menuItemNew.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                menuItemNewAction();
            }

            private void menuItemNewAction() {
                System.out.println("oh my god");
            }
        });  
        
        menuItemOpen.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();
                configureFileChooser(fileChooser);
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    System.out.println("it should open something. ");
                }
            }

            private void configureFileChooser(FileChooser fileChooser) {
                fileChooser.setTitle("Choose which file to save to ");
                fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("IPR", "*.ipr")
                );
            }
        });  
        
        menuItemSave.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if(currentFile != null) { 
                    PrintWriter writer = null;
                    try {
                        writer = new PrintWriter(currentFile, "UTF-8");
                        saveInPrintWriter(writer);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Prove.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(Prove.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        writer.close();
                    }
                } else { 
                    FileChooser fileChooser = new FileChooser();
                    configureFileChooser(fileChooser);
                    File file = fileChooser.showSaveDialog(stage);

                    if (file != null) {
                        if(!file.getName().contains(".ipr")) { 
                            file = new File(file.getAbsolutePath()+".ipr");
                        }
                        PrintWriter writer = null;
                        try {
                            writer = new PrintWriter(file, "UTF-8");
                            saveInPrintWriter(writer);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Prove.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(Prove.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            currentFile = file;
                            writer.close();
                        }
                    }
                }
                
            }
            
            private void configureFileChooser(FileChooser fileChooser) {
                fileChooser.setTitle("Save file ");
                fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("IPR", "*.ipr")
                );
            }

            private void saveInPrintWriter(PrintWriter writer) {
                for (LogicStatement l:startStatements) { 
                    writer.println(l);
                }
                writer.println("\n");
                for (ProveLine p:items) { 
                    writer.println(p);
                }
                writer.println("\n");
                writer.println(goalStatement);
            }
            
        });  
        
        menuItemSaveAs.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();
                configureFileChooser(fileChooser);
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    if(!file.getName().contains(".ipr")) { 
                        file = new File(file.getAbsolutePath()+".ipr");
                    }
                    PrintWriter writer = null;
                    try {
                        writer = new PrintWriter(file, "UTF-8");
                        saveInPrintWriter(writer);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Prove.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedEncodingException ex) {
                       Logger.getLogger(Prove.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        currentFile = file;
                        writer.close();
                    }
                }
            }

            private void configureFileChooser(FileChooser fileChooser) {
                fileChooser.setTitle("Choose which file to save to ");
                fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("IPR", "*.ipr")
                );
            }

            private void saveInPrintWriter(PrintWriter writer) {
                for (LogicStatement l:startStatements) { 
                    writer.println(l);
                }
                writer.println("0sf");
                for (ProveLine p:items) { 
                    if(p instanceof TwoBoxStartingLine) { 
                        writer.println("0tbs"); 
                    } else if(p instanceof TwoBoxClosingLine) { 
                        writer.println("0tbc"); 
                    } else if(p instanceof boxStartingLine) { 
                        writer.println("0bs"); 
                    } else if(p instanceof boxClosingLine) { 
                        writer.println("0bc"); 
                    } 
                    if(p.getFml().getText().equals("")) { 
                        writer.println("0empty");
                    } else { 
                        writer.println(p.getFml().getText());
                    }
                }
                writer.println("0pf");
                writer.println(goalStatement);
            }

            
        });  
        
        menuItemImport.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                menuItemNewAction();
            }

            private void menuItemNewAction() {
                System.out.println("oh my god");
            }
        });  
        
        menuItemClose.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                menuItemNewAction();
            }

            private void menuItemNewAction() {
                System.out.println("oh my god");
            }
        });  
        
        menuItemClearBracket.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                menuItemNewAction();
            }

            private void menuItemNewAction() {
                System.out.println("oh my god");
            }
        });  
        
        menuItemAddBracket.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                menuItemNewAction();
            }

            private void menuItemNewAction() {
                System.out.println("oh my god");
            }
        });  
        
        menuItemAbout.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                menuItemNewAction();
            }

            private void menuItemNewAction() {
                System.out.println("oh my god");
            }
        });  
        
        createTooltip();
        colorRuleBar();
        
    }
    
    private void createTooltip() { 
        Tooltip andIT = new Tooltip();
        Tooltip andET = new Tooltip();
        Tooltip impliesIT = new Tooltip();
        Tooltip impliesET = new Tooltip();
        Tooltip orIT = new Tooltip();
        Tooltip orET = new Tooltip();
        Tooltip truthIT = new Tooltip();
        Tooltip falsityIT = new Tooltip();
        Tooltip falsityET = new Tooltip();
        Tooltip IFFIT = new Tooltip();
        Tooltip IFFET = new Tooltip();
        Tooltip notIT = new Tooltip();
        Tooltip notET = new Tooltip();
        Tooltip notnotT = new Tooltip();
        Tooltip assT = new Tooltip();
        Tooltip lemmaT = new Tooltip();
        Tooltip tickT = new Tooltip();
        Tooltip createBoxT = new Tooltip();
        Tooltip createBoxTwoT = new Tooltip();
        Tooltip createNewLineT = new Tooltip();
        Tooltip PCT = new Tooltip();
                
        andIT.setText("1  A\n2  B\n3  A∧B  ∧I(1,2)");
        andIT.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        andI.setTooltip(andIT);
        
        andET.setText("1  A∧B\n2  A  ∧E(1)\n3  B  ∧E(1)\n");
        andET.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        andE.setTooltip(andET);
        
        impliesIT.setText("1  A\n2  B\n3  A→B  →I(1,2)\nasdf");
        impliesIT.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        impliesI.setTooltip(impliesIT);
        
        impliesET.setText("Tooltip for Button\nasdfasd");
        impliesET.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        impliesE.setTooltip(impliesET);
        
        orIT.setText("Tooltip for Button\nasdfasd");
        orIT.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        orI.setTooltip(orIT);
        
        orET.setText("Tooltip for Button\nasdfasd");
        orET.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        orE.setTooltip(orET);
        
        truthIT.setText("Tooltip for Button\nasdfasd");
        truthIT.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        truthI.setTooltip(truthIT);
        
        falsityIT.setText("Tooltip for Button\nasdfasd");
        falsityIT.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        falsityI.setTooltip(falsityIT);
        
        falsityET.setText("Tooltip for Button\nasdfasd");
        falsityET.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        falsityE.setTooltip(falsityET);
        
        IFFIT.setText("Tooltip for Button\nasdfasd");
        IFFIT.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        IFFI.setTooltip(IFFIT);
        
        IFFET.setText("Tooltip for Button\nasdfasd");
        IFFET.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        IFFE.setTooltip(IFFET);
        
        notIT.setText("Tooltip for Button\nasdfasd");
        notIT.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        notI.setTooltip(notIT);
        
        notET.setText("Tooltip for Button\nasdfasd");
        notET.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        notE.setTooltip(notET);
        
        notnotT.setText("Tooltip for Button\nasdfasd");
        notnotT.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        notnot.setTooltip(notnotT);
        
        assT.setText("Tooltip for Button\nasdfasd");
        assT.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        ass.setTooltip(assT);
        
        lemmaT.setText("Tooltip for Button\nasdfasd");
        lemmaT.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        lemma.setTooltip(lemmaT);
        
        tickT.setText("Tooltip for Button\nasdfasd");
        tickT.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        tick.setTooltip(tickT);
        
        createBoxT.setText("Tooltip for Button\nasdfasd");
        createBoxT.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        createBox.setTooltip(createBoxT);
        
        createBoxTwoT.setText("Tooltip for Button\nasdfasd");
        createBoxTwoT.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        createBoxTwo.setTooltip(createBoxTwoT);
        
        createNewLineT.setText("Tooltip for Button\nasdfasd");
        createNewLineT.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        createNewLine.setTooltip(createNewLineT);
        
        PCT.setText("Tooltip for Button\nasdfasd");
        PCT.setStyle("-fx-background-color:#fffacd; -fx-text-fill:black;");
        PC.setTooltip(PCT);
        
    }
    
    private void colorRuleBar() { 
        ObservableList<Node> childrens = ruleBar.getChildren();
        for(Node node: childrens) { 
            if(node instanceof Pane) { 
                if(ruleBar.getColumnIndex(node) % 2 != 0) { 
                    node.setStyle("-fx-background-color: #dcdcdc;");
                }
            }
        }
    }
    
    @FXML
    public void redirectprove(Stage stage, List<LogicStatement> sf, LogicStatement gf) { 
        
        this.startStatements = sf; 
        this.goalStatement = gf; 
        proveView = new ListView<ProveLine>();
        items = observableArrayList();
        problemList = new ListView<HBox>();
        problemHBox = observableArrayList();
        boxes = new ArrayList<boxStartingLine>();
        
        
        //StartFomulars.setText(startStatements.toString());
        //GoalFomular.setText(goalStatement.toString());
        
        starBox = new VBox();
        for (LogicStatement l:sf) { 
            
            givenLineNum++;
            currentMaxLine++;
            GivenLine gl = new GivenLine(currentMaxLine, l.toString(), "Given"); 
            starBox.getChildren().add(gl); 
            
        }
        
        goalLine = new GivenLine(0, gf.toString(), "");
        
        //currentMaxLine++;
        //items.add(new ProveLine(currentMaxLine));
        proveView.setItems(items);
        provePane.add(starBox,1,0);
        provePane.add(goalLine, 1, 2);
        provePane.add(proveView, 1, 1);
        starBox.setAlignment(Pos.CENTER);
        goalLine.setAlignment(Pos.CENTER);
        
        
        
        stage.setScene(scene);
        stage.hide();
        stage.show();
        
    }
    @FXML 
    public void checkButtonAction(ActionEvent event) throws Exception{ 
        
        boolean result = true; 
        //check args
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("problemShooter.fxml"));
        fxmlLoader.setController(this);
        try {
            root = fxmlLoader.load();
            problemStage = new Stage();
            problemStage.setTitle("Check");
            problemStage.setScene(new Scene(root, 384, 512));
            problemStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        problemList.setItems(problemHBox);
        problemHBox.removeAll(problemHBox);
        //check meaning 
        for (ProveLine p:items) { 
            if (!checkProveLine(p)) { 
                result = false; 
            } else { 
            }
        }
        if(items.size()>0) { 
            if(result && !stringToLS(items.get(items.size()-1).getFml().getText()).equalsTo(goalStatement)) { 
                addToProblemList("Result:", "You did well, carry on and get the result. ");
            } else if (result && stringToLS(items.get(items.size()-1).getFml().getText()).equalsTo(goalStatement)) { 
                addToProblemList("Result:", "Congratulations! You have got the solution! ");
            } else { 
                addToProblemList("Result:", "Something's gone wrong. Keep calm and carry on! ");
            }
        } else { 
            addToProblemList("Result:", "Ok. Don't be lazy, Let's get start.");
        }
        
        
        
    }
    private void addToProblemList(String i, String s) { 
        ProblemAndErrors p = new ProblemAndErrors(i, s);
        problemHBox.add(p);
    }
    
    private boolean checkProveLine(ProveLine p) throws Exception{ 
        //check if empty
        //TODO
        String ruleName = p.getRule(); 
        if (p.getFml().getText().equals("")) { 
            addToProblemList(new Integer(p.getNum()).toString(), "This line is empty. ");
            return false; 
        }
        if(!p.getRuled()) { 
            addToProblemList(new Integer(p.getNum()).toString(), "This line does not have a rule. ");
            return false; 
        }
        //check if correct
        LogicStatement fml = stringToLS(p.getFml().getText());
        //if it has arguments 
        if(p.haveArguments()) { 
            //check if the given aruments is valid
            //get array of arguments
            LogicStatement[] argumentsStatement = new LogicStatement[p.getArguments().length];
            List<HBox> argumentLineNumber = new ArrayList<HBox>();
            for(int i = 0; i < p.getArguments().length; i++) { 
                if(p.getArguments()[i]==0) { 
                    addToProblemList(new Integer(p.getNum()).toString(), "This line has not enough arguments or the input arguments are not number. "); 
                    return false; 
                } else if (p.getArguments()[i]>currentMaxLine) { 
                    addToProblemList(new Integer(p.getNum()).toString(), "The given lines is invalid to be the arguments for the current line(You can not use these given lines as argument. )");
                    return false;
                } else { 
                    argumentLineNumber.add(findLine(p.getArguments()[i]));
                    argumentsStatement[i] = stringToLS(findLineToString(p.getArguments()[i]));
                }
                
            } 
            
            
            if(!checkLegalArgs(argumentLineNumber, p.getLegalArgs())) { 
                addToProblemList(new Integer(p.getNum()).toString(), "The given lines is invalid to be the arguments for the current line(You can not use these given lines as argument. )");
                return false; 
            }
            
            
            //check if the rules is applied correctly
            switch(ruleName) {
                case "∧I": return checkAndI(p.getNum(), fml, new AndStatement(argumentsStatement[0], argumentsStatement[1])); 
                case "∧E": return checkAndE(p.getNum(), fml, argumentsStatement[0]);
                case "→E": return checkImpliesE(p.getNum(), fml, argumentsStatement[0], argumentsStatement[1]);
                case "∨I": return checkOrI(p.getNum(), fml,argumentsStatement[0]); 
                case "⊥I": return checkFalsityI(p.getNum(), fml, argumentsStatement[0], argumentsStatement[1]);
                case "⊥E": return checkFalsityE(p.getNum(), fml, argumentsStatement[0]);
                case "↔I": return checkIFFI(p.getNum(), fml, argumentsStatement[0], argumentsStatement[1]);
                case "↔E": return checkIFFE(p.getNum(), fml, argumentsStatement[0], argumentsStatement[1]); 
                case "¬E": return checkNotE(p.getNum(), fml, argumentsStatement[0], argumentsStatement[1]);
                case "¬¬": return checkNotNot(p.getNum(), fml, argumentsStatement[0]);
                    //2 args, need a box
                case "→I": return checkImpliesI(p.getNum(), p, p.getArguments()[0], p.getArguments()[1]);
                    //2 args, need a box
                case "¬I": return checkNotI(p.getNum(), p, p.getArguments()[0], p.getArguments()[1]);
                    //5 args, need two boxes 
                case "∨E": return checkOrE(p.getNum(), p, p.getArguments()[0], p.getArguments()[1], p.getArguments()[2], p.getArguments()[3], p.getArguments()[4]);
                    //2 args, need a box
                case "PC": return checkPC(p.getNum(), fml, argumentsStatement[0], argumentsStatement[1]); 
                    
                case "✔":return checkTick(p.getNum(), fml, argumentsStatement[0]);
                default: return false;  
            }
        }        else { 
            switch(ruleName) {
                case "⊤I": return checkTruthI(p.getNum(), fml);
                case "ass": return checkAss(p.getNum(), p);
                case "lemma": return checkLemma(p.getNum(), fml);
            }
        }
        return false;
    }
    
    private boolean checkLegalArgs(List<HBox> l1, List<HBox> l2) { 
        if(l2.containsAll(l1)) { 
            return true;
        } else { 
            return false; 
        }
    }
    
    private void updateLegalArgs(ProveLine pl) {
        int position = pl.getNum()-givenLineNum-1;
        List<ProveLine> below = new ArrayList<ProveLine>(); 
        
        if(position != items.size()) {
            for(int j = position+1; j<items.size(); j++) { 
                below.add(items.get(j));
            }
        }
        //if its first line and not a boxstarting line
        if(checkIfFirstLine(pl) && !(pl instanceof boxStartingLine)) { 
            for(int i = 0; i < givenLineNum; i++) { 
                pl.addLegalArgs((HBox) starBox.getChildren().get(i));
            }
            for(ProveLine plpl: below) { 
                plpl.addLegalArgs(pl);
            }
        } 
        //if its first line and a boxstarting line
        else if(checkIfFirstLine(pl) && pl instanceof boxStartingLine) { 
            for(int i = 0; i < givenLineNum; i++) { 
                pl.addLegalArgs((HBox) starBox.getChildren().get(i));
            }
        } 
        //if its not a first line 
        else { 
            ProveLine upper = items.get(pl.getNum()-givenLineNum-2);
            
            //if its a boxstarting line or boxclosing line 
            if(pl instanceof boxStartingLine || pl instanceof boxClosingLine) { 
                //if upper is twoboxc 
                if(upper instanceof TwoBoxClosingLine) { 
                    if(!((TwoBoxClosingLine) upper).getStartLine().getFirst()) { 
                        for(HBox v:((boxClosingLine)upper).getStartLine().getLegalArgs()) { 
                            pl.addLegalArgs(v);
                        }

                        pl.addLegalArgs(upper);
                        pl.addLegalArgs(((boxClosingLine)upper).getStartLine());
                        pl.addLegalArgs(((TwoBoxClosingLine) upper).getStartLine().getPair());
                        pl.addLegalArgs(((TwoBoxClosingLine) upper).getStartLine().getPair().getEndLine());
                    } else { 
                        for(HBox v:((boxClosingLine)upper).getStartLine().getLegalArgs()) { 
                            pl.addLegalArgs(v);
                        }

                        pl.addLegalArgs(upper);
                        pl.addLegalArgs(((boxClosingLine)upper).getStartLine());
                    }
                }
                //if upper is boxc
                else if(upper instanceof boxClosingLine) { 
                    for(HBox v:((boxClosingLine)upper).getStartLine().getLegalArgs()) { 
                        pl.addLegalArgs(v);
                    }
                    pl.addLegalArgs(upper);
                    pl.addLegalArgs(((boxClosingLine)upper).getStartLine());
                } else { 
                    //add everything from upper
                    for(HBox k:upper.getLegalArgs()) { 
                        pl.addLegalArgs(k);
                    }
                    //if upper is immediateafter of two, need to take four off 
                    if((pl.getNum()-givenLineNum-2)!=0 && items.get(pl.getNum()-givenLineNum-3) instanceof TwoBoxClosingLine) { 
                        if(((TwoBoxClosingLine) items.get(pl.getNum()-givenLineNum-3)).getStartLine().getFirst()) { 
                            
                        } else { 
                            pl.removeLegalArgs(findParentBox((ProveLine) findLine(pl.getNum()-2)));
                            pl.removeLegalArgs(findParentBox((ProveLine) findLine(pl.getNum()-2)).getEndLine());
                            pl.removeLegalArgs(((TwoBoxStartingLine) findParentBox((ProveLine) findLine(pl.getNum()-2))).getPair());
                            pl.removeLegalArgs(((TwoBoxStartingLine) findParentBox((ProveLine) findLine(pl.getNum()-2))).getPair().getEndLine());
                        }
                        
                    } 
                    //if upper is immediateafter of two, need to take four off 
                    if((pl.getNum()-givenLineNum-2)!=0 && items.get(pl.getNum()-givenLineNum-3) instanceof TwoBoxClosingLine) { 
                        if(((TwoBoxClosingLine) items.get(pl.getNum()-givenLineNum-3)).getStartLine().getFirst()) { 
                            
                        } else { 
                            pl.removeLegalArgs(findParentBox((ProveLine) findLine(pl.getNum()-2)));
                            pl.removeLegalArgs(findParentBox((ProveLine) findLine(pl.getNum()-2)).getEndLine());
                            pl.removeLegalArgs(((TwoBoxStartingLine) findParentBox((ProveLine) findLine(pl.getNum()-2))).getPair());
                            pl.removeLegalArgs(((TwoBoxStartingLine) findParentBox((ProveLine) findLine(pl.getNum()-2))).getPair().getEndLine());
                        }
                        
                    } 
                    //if upper is immediateafter, need to take boxs and boxc off
                    else if((pl.getNum()-givenLineNum-2)!=0 && items.get(pl.getNum()-givenLineNum-3) instanceof boxClosingLine) { 
                        pl.removeLegalArgs(findParentBox((ProveLine) findLine(pl.getNum()-2)));
                        pl.removeLegalArgs(findParentBox((ProveLine) findLine(pl.getNum()-2)).getEndLine());
                    }
                    
                    //add upper
                    pl.addLegalArgs(upper);
                }
                
                if(!items.get(items.size()-1).equals(pl) && pl instanceof TwoBoxClosingLine&&pl.getNum()-givenLineNum-3>=0) { 
                    if(!((TwoBoxClosingLine) pl).getStartLine().getFirst()) { 
                        //if there is a twobox above my startline, remove the oneBelow's previous boxs and boxc, add add current back
                        if(items.get(pl.getNum()-givenLineNum-3) instanceof TwoBoxClosingLine) { 
                            if(!((TwoBoxClosingLine) items.get(pl.getNum()-givenLineNum-3)).getStartLine().getFirst()) { 
                                items.get(pl.getNum()-givenLineNum).removeLegalArgs(items.get(pl.getNum()-givenLineNum-3));
                                items.get(pl.getNum()-givenLineNum).removeLegalArgs(((boxClosingLine)items.get(pl.getNum()-givenLineNum-3)).getStartLine());
                                items.get(pl.getNum()-givenLineNum).removeLegalArgs(((TwoBoxClosingLine) items.get(pl.getNum()-givenLineNum-3)).getStartLine().getPair());
                                items.get(pl.getNum()-givenLineNum).removeLegalArgs(((TwoBoxClosingLine) items.get(pl.getNum()-givenLineNum-3)).getStartLine().getPair().getEndLine());
                                items.get(pl.getNum()-givenLineNum).addLegalArgs(pl);
                                items.get(pl.getNum()-givenLineNum).addLegalArgs(((TwoBoxClosingLine) pl).getStartLine());
                                items.get(pl.getNum()-givenLineNum).addLegalArgs(((TwoBoxClosingLine) pl).getStartLine().getPair());
                                items.get(pl.getNum()-givenLineNum).addLegalArgs(((TwoBoxClosingLine) pl).getStartLine().getPair().getEndLine());
                            }
                        }
                        //if there is a box above my startline, remove the oneBelow's previous boxs and boxc, add add current back
                        else if(items.get(pl.getNum()-givenLineNum-3) instanceof boxClosingLine) { 
                            items.get(pl.getNum()-givenLineNum).removeLegalArgs(items.get(pl.getNum()-givenLineNum-3));
                            items.get(pl.getNum()-givenLineNum).removeLegalArgs(((boxClosingLine)items.get(pl.getNum()-givenLineNum-3)).getStartLine());
                            items.get(pl.getNum()-givenLineNum).addLegalArgs(pl);
                            items.get(pl.getNum()-givenLineNum).addLegalArgs(((TwoBoxClosingLine) pl).getStartLine());
                            items.get(pl.getNum()-givenLineNum).addLegalArgs(((TwoBoxClosingLine) pl).getStartLine().getPair());
                            items.get(pl.getNum()-givenLineNum).addLegalArgs(((TwoBoxClosingLine) pl).getStartLine().getPair().getEndLine());
                        } else { 
                            items.get(pl.getNum()-givenLineNum).addLegalArgs(pl);
                            items.get(pl.getNum()-givenLineNum).addLegalArgs(((TwoBoxClosingLine) pl).getStartLine());
                            items.get(pl.getNum()-givenLineNum).addLegalArgs(((TwoBoxClosingLine) pl).getStartLine().getPair());
                            items.get(pl.getNum()-givenLineNum).addLegalArgs(((TwoBoxClosingLine) pl).getStartLine().getPair().getEndLine());
                        }
                    }
                }
                //if one below exist, i am a boxc
                if(!items.get(items.size()-1).equals(pl) && pl instanceof boxClosingLine && pl.getNum()-givenLineNum-3>=0) { 
                    //if there is a twobox above my startline, remove the oneBelow's previous boxs and boxc, add add current back
                    if(items.get(pl.getNum()-givenLineNum-3) instanceof TwoBoxClosingLine) { 
                        if(!((TwoBoxClosingLine) items.get(pl.getNum()-givenLineNum-3)).getStartLine().getFirst()) { 
                            items.get(pl.getNum()-givenLineNum).removeLegalArgs(items.get(pl.getNum()-givenLineNum-3));
                            items.get(pl.getNum()-givenLineNum).removeLegalArgs(((boxClosingLine)items.get(pl.getNum()-givenLineNum-3)).getStartLine());
                            items.get(pl.getNum()-givenLineNum).removeLegalArgs(((TwoBoxClosingLine) items.get(pl.getNum()-givenLineNum-3)).getStartLine().getPair());
                            items.get(pl.getNum()-givenLineNum).removeLegalArgs(((TwoBoxClosingLine) items.get(pl.getNum()-givenLineNum-3)).getStartLine().getPair().getEndLine());
                            items.get(pl.getNum()-givenLineNum).addLegalArgs(pl);
                            items.get(pl.getNum()-givenLineNum).addLegalArgs(((boxClosingLine) pl).getStartLine());
                        }
                    }
                    //if there is a box above my startline, remove the oneBelow's previous boxs and boxc, add add current back
                    else if(items.get(pl.getNum()-givenLineNum-3) instanceof boxClosingLine) { 
                        items.get(pl.getNum()-givenLineNum).removeLegalArgs(items.get(pl.getNum()-givenLineNum-3));
                        items.get(pl.getNum()-givenLineNum).removeLegalArgs(((boxClosingLine)items.get(pl.getNum()-givenLineNum-3)).getStartLine());
                        items.get(pl.getNum()-givenLineNum).addLegalArgs(pl);
                        items.get(pl.getNum()-givenLineNum).addLegalArgs(((boxClosingLine) pl).getStartLine());
                    } else { 
                        items.get(pl.getNum()-givenLineNum).addLegalArgs(pl);
                        items.get(pl.getNum()-givenLineNum).addLegalArgs(((boxClosingLine) pl).getStartLine());
                    }
                    
                } 
                
            } 
            //if its in box 
            else if(pl.isInBox()) { 
                 if(upper instanceof TwoBoxClosingLine) { 
                    if(!((TwoBoxClosingLine) upper).getStartLine().getFirst()) { 
                        for(HBox v:((boxClosingLine)upper).getStartLine().getLegalArgs()) { 
                            pl.addLegalArgs(v);
                        }

                        pl.addLegalArgs(upper);
                        pl.addLegalArgs(((boxClosingLine)upper).getStartLine());
                        pl.addLegalArgs(((TwoBoxClosingLine) upper).getStartLine().getPair());
                        pl.addLegalArgs(((TwoBoxClosingLine) upper).getStartLine().getPair().getEndLine());
                    } else { 
                        for(HBox v:((boxClosingLine)upper).getStartLine().getLegalArgs()) { 
                            pl.addLegalArgs(v);
                        }

                        pl.addLegalArgs(upper);
                        pl.addLegalArgs(((boxClosingLine)upper).getStartLine());
                    }
                }
                //if upper is boxc
                else if(upper instanceof boxClosingLine) { 
                    
                    for(HBox v:((boxClosingLine)upper).getStartLine().getLegalArgs()) { 
                        pl.addLegalArgs(v);
                    }
                    
                    pl.addLegalArgs(upper);
                    pl.addLegalArgs(((boxClosingLine)upper).getStartLine());
                    
                } else { 
                    //add everything from upper
                    for(HBox k:upper.getLegalArgs()) { 
                        pl.addLegalArgs(k);
                    }
                    //if upper is immediateafter of two, need to take four off 
                    if((pl.getNum()-givenLineNum-2)!=0 && items.get(pl.getNum()-givenLineNum-3) instanceof TwoBoxClosingLine) { 
                        if(((TwoBoxClosingLine) items.get(pl.getNum()-givenLineNum-3)).getStartLine().getFirst()) { 
                            
                        } else { 
                            pl.removeLegalArgs(findParentBox((ProveLine) findLine(pl.getNum()-2)));
                            pl.removeLegalArgs(findParentBox((ProveLine) findLine(pl.getNum()-2)).getEndLine());
                            pl.removeLegalArgs(((TwoBoxStartingLine) findParentBox((ProveLine) findLine(pl.getNum()-2))).getPair());
                            pl.removeLegalArgs(((TwoBoxStartingLine) findParentBox((ProveLine) findLine(pl.getNum()-2))).getPair().getEndLine());
                        }
                        
                    } 
                    //if upper is immediateafter, need to take boxs and boxc off
                    else if((pl.getNum()-givenLineNum-2)!=0 && items.get(pl.getNum()-givenLineNum-3) instanceof boxClosingLine) { 
                        pl.removeLegalArgs(findParentBox((ProveLine) findLine(pl.getNum()-2)));
                        pl.removeLegalArgs(findParentBox((ProveLine) findLine(pl.getNum()-2)).getEndLine());
                    }
                    //add upper
                    pl.addLegalArgs(upper);
                }
                
                
                //everyline below self, in the same box, add self
                for(ProveLine plpl:findParentBox(pl).getLineInBox()) { 
                    if(below.contains(plpl)) { 
                        plpl.addLegalArgs(pl);
                    }
                }
                //if oneupper is twoboxc
                if(upper instanceof TwoBoxClosingLine && findParentBox(pl).getLineInBox().contains(items.get(pl.getNum()-givenLineNum)) && !items.get(pl.getNum()-givenLineNum).equals(findParentBox(pl).getEndLine())) { 
                    if(!((TwoBoxClosingLine) upper).getStartLine().getFirst()) { 
                        items.get(pl.getNum()-givenLineNum).removeLegalArgs(upper);
                        items.get(pl.getNum()-givenLineNum).removeLegalArgs(((boxClosingLine)upper).getStartLine());
                        items.get(pl.getNum()-givenLineNum).removeLegalArgs(((TwoBoxClosingLine) upper).getStartLine().getPair());
                        items.get(pl.getNum()-givenLineNum).removeLegalArgs(((TwoBoxClosingLine) upper).getStartLine().getPair().getEndLine());
                    }
                } 
                //if oneupper is boxc
                else if(upper instanceof boxClosingLine && findParentBox(pl).getLineInBox().contains(items.get(pl.getNum()-givenLineNum)) && !items.get(pl.getNum()-givenLineNum).equals(findParentBox(pl).getEndLine())) { 
                    items.get(pl.getNum()-givenLineNum).removeLegalArgs(upper);
                    items.get(pl.getNum()-givenLineNum).removeLegalArgs(((boxClosingLine)upper).getStartLine());
                }
                
            }  
            //if its a normal line
            else { 
                 if(upper instanceof TwoBoxClosingLine) { 
                    if(!((TwoBoxClosingLine) upper).getStartLine().getFirst()) { 
                        for(HBox v:((boxClosingLine)upper).getStartLine().getLegalArgs()) { 
                            pl.addLegalArgs(v);
                        }

                        pl.addLegalArgs(upper);
                        pl.addLegalArgs(((boxClosingLine)upper).getStartLine());
                        pl.addLegalArgs(((TwoBoxClosingLine) upper).getStartLine().getPair());
                        pl.addLegalArgs(((TwoBoxClosingLine) upper).getStartLine().getPair().getEndLine());
                    } else { 
                        for(HBox v:((boxClosingLine)upper).getStartLine().getLegalArgs()) { 
                            pl.addLegalArgs(v);
                        }

                        pl.addLegalArgs(upper);
                        pl.addLegalArgs(((boxClosingLine)upper).getStartLine());
                    }
                }
                //if upper is boxc
                else if(upper instanceof boxClosingLine) { 
                    for(HBox v:((boxClosingLine)upper).getStartLine().getLegalArgs()) { 
                        pl.addLegalArgs(v);
                    }
                    pl.addLegalArgs(upper);
                    pl.addLegalArgs(((boxClosingLine)upper).getStartLine());
                    
                } else { 
                    //add everything from upper
                    for(HBox k:upper.getLegalArgs()) { 
                        pl.addLegalArgs(k);
                    }
                    //if upper is immediateafter of two, need to take four off 
                    if((pl.getNum()-givenLineNum-2)!=0 && items.get(pl.getNum()-givenLineNum-3) instanceof TwoBoxClosingLine) { 
                        if(((TwoBoxClosingLine) items.get(pl.getNum()-givenLineNum-3)).getStartLine().getFirst()) { 
                            
                        } else { 
                            pl.removeLegalArgs(findParentBox((ProveLine) findLine(pl.getNum()-2)));
                            pl.removeLegalArgs(findParentBox((ProveLine) findLine(pl.getNum()-2)).getEndLine());
                            pl.removeLegalArgs(((TwoBoxStartingLine) findParentBox((ProveLine) findLine(pl.getNum()-2))).getPair());
                            pl.removeLegalArgs(((TwoBoxStartingLine) findParentBox((ProveLine) findLine(pl.getNum()-2))).getPair().getEndLine());
                        }
                        
                    } 
                    //if upper is immediateafter, need to take boxs and boxc off
                    else if((pl.getNum()-givenLineNum-2)!=0 && items.get(pl.getNum()-givenLineNum-3) instanceof boxClosingLine) { 
                        pl.removeLegalArgs(findParentBox((ProveLine) findLine(pl.getNum()-2)));
                        pl.removeLegalArgs(findParentBox((ProveLine) findLine(pl.getNum()-2)).getEndLine());
                    }
                    //add upper
                    pl.addLegalArgs(upper);
                }
                
                
                //everyline below self, add self
                if (!items.get(items.size()-1).equals(pl)) { 
                    for(ProveLine plpl:below) { 
                        if(!plpl.isInBox() || plpl.isInBox() && (plpl instanceof boxStartingLine || plpl instanceof boxClosingLine)) { 
                            plpl.addLegalArgs(pl);
                        }
                    }
                     //if oneupper is twoboxc
                    if(upper instanceof TwoBoxClosingLine) { 
                        if(!((TwoBoxClosingLine) upper).getStartLine().getFirst()) { 
                            items.get(pl.getNum()-givenLineNum).removeLegalArgs(upper);
                            items.get(pl.getNum()-givenLineNum).removeLegalArgs(((boxClosingLine)upper).getStartLine());
                            items.get(pl.getNum()-givenLineNum).removeLegalArgs(((TwoBoxClosingLine) upper).getStartLine().getPair());
                            items.get(pl.getNum()-givenLineNum).removeLegalArgs(((TwoBoxClosingLine) upper).getStartLine().getPair().getEndLine());
                        }
                    } 
                    //if oneupper is boxc
                    else if(upper instanceof boxClosingLine) { 
                        items.get(pl.getNum()-givenLineNum).removeLegalArgs(upper);
                        items.get(pl.getNum()-givenLineNum).removeLegalArgs(((boxClosingLine)upper).getStartLine());
                    }
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
    
    //input Line number, return the given/prove line.toString() with that line number 
    private String findLineToString(int i) { 
        if(i<=givenLineNum) { 
            return ((GivenLine)starBox.getChildren().get(i-1)).getfml();
        } else { 
            return items.get(i-givenLineNum-1).getFml().getText();
        }
    }
    
    //input Line number, return the given/prove line.toString() with that line number 
    private HBox findLine(int i) { 
        if(i<=givenLineNum) { 
            return ((GivenLine)starBox.getChildren().get(i-1));
        } else { 
            return items.get(i-givenLineNum-1);
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
    
    
    private boolean checkAndI(int ipp, LogicStatement s, AndStatement a) { 
        if(s instanceof AndStatement) { 
            
            if(((AndStatement)s).equalsTo(a)) { 
                addToProblemList(new Integer(ipp).toString(), "✔");
                return true;
            } else { 
                addToProblemList(new Integer(ipp).toString(), "The provided two lines can not produce this line by AndI. ");
                return false; 
            }
        } else { 
            addToProblemList(new Integer(ipp).toString(), "AndI can not be applied on a none AndStatemnet. ");
            return false;
        }
    }
    
    private boolean checkAndE(int ipp, LogicStatement s, LogicStatement a){ 
        if(a instanceof AndStatement) { 
            if(((AndStatement) a).nestedStatementLeft.equalsTo(s) || ((AndStatement) a).nestedStatementRight.equalsTo(s)) { 
                addToProblemList(new Integer(ipp).toString(), "✔");
                return true;
            } else { 
                addToProblemList(new Integer(ipp).toString(), "The provided line can not produce this line by AndE. ");
                return false; 
            }
        } else { 
            addToProblemList(new Integer(ipp).toString(), "The provided line is not an AndStatement. ");
            return false;
        }
    }
    
    private boolean checkImpliesE(int ipp, LogicStatement s, LogicStatement a1, LogicStatement a2) { 
        if(a1 instanceof ImpliesStatement && ((ImpliesStatement) a1).nestedStatementLeft.equalsTo(a2)|| a2 instanceof ImpliesStatement && ((ImpliesStatement) a2).nestedStatementLeft.equalsTo(a1)) { 
            if(a1 instanceof ImpliesStatement && ((ImpliesStatement) a1).nestedStatementLeft.equalsTo(a2) && !((ImpliesStatement) a1).nestedStatementRight.equalsTo(s)) { 
                addToProblemList(new Integer(ipp).toString(), "This line should be on the right hand side of the first provided line. ");
                return false; 
            } else if (a2 instanceof ImpliesStatement && ((ImpliesStatement) a2).nestedStatementLeft.equalsTo(a1) && !((ImpliesStatement) a2).nestedStatementRight.equalsTo(s)) { 
                addToProblemList(new Integer(ipp).toString(), "This line should be on the right hand side of the second provided line. ");
                return false; 
            } else { 
                addToProblemList(new Integer(ipp).toString(), "✔");
                return true; 
            }
        } else { 
            addToProblemList(new Integer(ipp).toString(), "One of the provided lines should be an impliesStatement produced by the other. ");
            return false; 
        }
    }
    
    private boolean checkOrI(int ipp, LogicStatement s, LogicStatement a) { 
        if(s instanceof OrStatement) { 
            if(((OrStatement) s).nestedStatementLeft.equalsTo(a) || ((OrStatement) s).nestedStatementRight.equalsTo(a)) { 
                addToProblemList(new Integer(ipp).toString(), "✔");
                return true;
            } else { 
                addToProblemList(new Integer(ipp).toString(), "The given line is not part of this OrStatement. ");
                return false; 
            }
        } else { 
            addToProblemList(new Integer(ipp).toString(), "The given line is not an OrStatement. ");
            return false;
        }
    }
    
    private boolean checkTruthI(int ipp, LogicStatement s) { 
        if (s instanceof Truth) { 
            addToProblemList(new Integer(ipp).toString(), "✔");
            return true;
        } else { 
            addToProblemList(new Integer(ipp).toString(), "Truth should be introduced. ");
            return false; 
        }
    }
    
    private boolean checkFalsityI(int ipp, LogicStatement s, LogicStatement a1, LogicStatement a2) { 
        if (s instanceof Falsity) {
            if (a2 instanceof NotStatement) {
                if (new NotStatement(a1).equalsTo(((NotStatement) a2))) { 
                    addToProblemList(new Integer(ipp).toString(), "✔");
                    return true;
                } else { 
                    addToProblemList(new Integer(ipp).toString(), "The second provided line should be a NotStatement created by first given line. ");
                    return false; 
                }
            } else if (a1 instanceof NotStatement) {
                if (new NotStatement(a2).equalsTo(((NotStatement) a1))) { 
                    addToProblemList(new Integer(ipp).toString(), "✔");
                    return true;
                } else { 
                    addToProblemList(new Integer(ipp).toString(), "The first provided line should be a NotStatement created by second given line. ");
                    return false; 
                }
            } else { 
                addToProblemList(new Integer(ipp).toString(), "One of the provided line should be a NotStatement. ");
                return false; 
            }
        } else { 
            addToProblemList(new Integer(ipp).toString(), "Falsity should be introduced. ");
            return false; 
        }
    }
    
    private boolean checkFalsityE(int ipp, LogicStatement s, LogicStatement a) { 
        if (a instanceof Falsity) { 
            addToProblemList(new Integer(ipp).toString(), "✔");
            return true;
        } else { 
            addToProblemList(new Integer(ipp).toString(), "The givne line should be falsity. ");
            return false; 
        }
    }
    
    private boolean checkIFFI(int ipp, LogicStatement s, LogicStatement a1, LogicStatement a2) { 
        if(s instanceof IFFStatement) { 
            if(a1 instanceof ImpliesStatement) {
                if(a2 instanceof ImpliesStatement) { 
                    if (((ImpliesStatement) a1).nestedStatementLeft.equalsTo(((ImpliesStatement) a2).nestedStatementRight) 
                        &&((ImpliesStatement) a1).nestedStatementRight.equalsTo(((ImpliesStatement) a2).nestedStatementLeft)) { 
                        if(((IFFStatement) s).nestedStatementLeft.equalsTo(((ImpliesStatement) a1).nestedStatementLeft) 
                            && ((IFFStatement) s).nestedStatementRight.equalsTo(((ImpliesStatement) a1).nestedStatementRight)) { 
                            addToProblemList(new Integer(ipp).toString(), "✔");
                            return true;
                        } else { 
                            addToProblemList(new Integer(ipp).toString(), "The provided two lines can not produce this formula by IFFI. ");
                            return false; 
                        } 
                    } else { 
                        addToProblemList(new Integer(ipp).toString(), "The left part of the first provided line should be same as the right part of the right part of the second provided line and vise versa. ");
                        return false; 
                    }
                } else { 
                    addToProblemList(new Integer(ipp).toString(), "The second provided line should be a ImpliesStatement. ");
                    return false; 
                }
            } else { 
                addToProblemList(new Integer(ipp).toString(), "The first provided line should be a ImpliesStatement. ");
                return false; 
            }
        } else { 
            addToProblemList(new Integer(ipp).toString(), "This line should be a IFFStatement. ");
            return false; 
        }
    }
    
    private boolean checkIFFE(int ipp, LogicStatement s, LogicStatement a1, LogicStatement a2) { 
        if (a1 instanceof IFFStatement) { 
            if (((IFFStatement) a1).nestedStatementLeft.equalsTo(a2) || ((IFFStatement) a1).nestedStatementRight.equalsTo(a2)) { 
                if(((IFFStatement) a1).nestedStatementLeft.equalsTo(a2) && ((IFFStatement) a1).nestedStatementRight.equalsTo(s)) { 
                    addToProblemList(new Integer(ipp).toString(), "✔");
                    return true;
                } else if(((IFFStatement) a1).nestedStatementLeft.equalsTo(s) && ((IFFStatement) a1).nestedStatementRight.equalsTo(a2)) { 
                    addToProblemList(new Integer(ipp).toString(), "✔");
                    return true; 
                } else { 
                    addToProblemList(new Integer(ipp).toString(), "The second given line should not be on the same side with this line");
                    return false; 
                }
            } else { 
                addToProblemList(new Integer(ipp).toString(), "The second given line should be a part of the first given line. ");
                return false;
            }
        } else if (a2 instanceof IFFStatement) { 
            if (((IFFStatement) a2).nestedStatementLeft.equalsTo(a1) || ((IFFStatement) a2).nestedStatementRight.equalsTo(a1)) { 
                if(((IFFStatement) a2).nestedStatementLeft.equalsTo(a1) && ((IFFStatement) a2).nestedStatementRight.equalsTo(s)) { 
                    addToProblemList(new Integer(ipp).toString(), "✔");
                    return true;
                } else if(((IFFStatement) a2).nestedStatementLeft.equalsTo(s) && ((IFFStatement) a2).nestedStatementRight.equalsTo(a1)) { 
                    addToProblemList(new Integer(ipp).toString(), "✔");
                    return true; 
                } else { 
                    addToProblemList(new Integer(ipp).toString(), "The first provided line should not be on the same side with this line");
                    return false; 
                }
            } else { 
                addToProblemList(new Integer(ipp).toString(), "The first provided line should be a part of the second provided line. ");
                return false;
            }
        } else { 
            addToProblemList(new Integer(ipp).toString(), "One of the provided line should be a IFFStatement. ");
            return false;
        }
    }
    
    private boolean checkNotE(int ipp, LogicStatement s, LogicStatement a1, LogicStatement a2) { 
        if (s instanceof Falsity) { 
            if(a1 instanceof NotStatement || a2 instanceof NotStatement) { 
                if(a1 instanceof NotStatement && new NotStatement(a2).equalsTo(a1)) { 
                    addToProblemList(new Integer(ipp).toString(), "✔");
                    return true;
                } else if(a2 instanceof NotStatement && new NotStatement(a1).equalsTo(a2)) { 
                    addToProblemList(new Integer(ipp).toString(), "✔");
                    return true; 
                } else { 
                    addToProblemList(new Integer(ipp).toString(), "One of the provided line should be a NotStatement produced by the other provided line. ");
                    return false; 
                }
                
            } else { 
                addToProblemList(new Integer(ipp).toString(), "One or the provided line should be a notStatement. ");
                return false; 
            }  
        } else { 
            addToProblemList(new Integer(ipp).toString(), "This line should be falsity. ");
            return false; 
        }
    }
    
    private boolean checkNotNot(int ipp, LogicStatement s, LogicStatement a) { 
        if (a instanceof NotStatement) { 
            if (((NotStatement) a).nestedStatement instanceof NotStatement) { 
                if (((NotStatement) ((NotStatement) a).nestedStatement).nestedStatement.equalsTo(s)) { 
                    addToProblemList(new Integer(ipp).toString(), "✔");
                    return true;
                } else { 
                    addToProblemList(new Integer(ipp).toString(), "This line should be a NotNotStatement produced by the first given line. ");
                    return false; 
                }
            } else { 
                addToProblemList(new Integer(ipp).toString(), "The given line should be a NotNotStatement. ");
                return false; 
            }
        } else { 
            addToProblemList(new Integer(ipp).toString(), "The given line should be a NotNotStatement. ");
            return false; 
        }
    }
    
    private boolean checkImpliesI(int ipp, ProveLine p, int i1, int i2) throws Exception { 
        if(i1 > givenLineNum && i2 >givenLineNum) { 
            ProveLine p1 = items.get(i1-givenLineNum-1); 
            ProveLine p2 = items.get(i2-givenLineNum-1);
            if(p.getNum()-1 == i2 &&  p2 instanceof boxClosingLine) { 
                if(p1 instanceof boxStartingLine) { 
                    if(((boxClosingLine) p2).getStartLine().equals((boxStartingLine)p1)) { 
                        if(p1.getRule()=="ass") { 
                            LogicStatement l1 = stringToLS(findLineToString(i1)); 
                            LogicStatement l2 = stringToLS(findLineToString(i2)); 
                            LogicStatement pp = stringToLS(p.getFml().getText());
                            if(pp instanceof ImpliesStatement) { 
                                if(((ImpliesStatement) pp).nestedStatementLeft.equalsTo(l1) && ((ImpliesStatement) pp).nestedStatementRight.equalsTo(l2)) { 
                                    addToProblemList(new Integer(ipp).toString(), "✔");
                                    return true;
                                } else { 
                                    addToProblemList(new Integer(ipp).toString(), "This line should be produced by the provided two lines. ");
                                    return false;
                                }
                            } else { 
                                addToProblemList(new Integer(ipp).toString(), "This line should be a impliesStatement. ");
                                return false;
                            }
                        } else { 
                            addToProblemList(new Integer(ipp).toString(), "The given line's rule shoud be assume. ");
                            return false;
                        }
                    } else { 
                        addToProblemList(new Integer(ipp).toString(), "The provided lines should be the boxs and boxc for the same box. ");
                        return false; 
                    }
                } else { 
                    addToProblemList(new Integer(ipp).toString(), "The provided line should be boxs. ");
                    return false; 
                }
            } else if(p.getNum()-1 == i1 &&  p1 instanceof boxClosingLine) { 
                if(p2 instanceof boxStartingLine) { 
                    if(((boxClosingLine) p1).getStartLine().equals((boxStartingLine)p2)) { 
                        if(p2.getRule()=="ass") { 
                            LogicStatement l1 = stringToLS(findLineToString(i2)); 
                            LogicStatement l2 = stringToLS(findLineToString(i1)); 
                            LogicStatement pp = stringToLS(p.getFml().getText());
                            if(pp instanceof ImpliesStatement) { 
                                if(((ImpliesStatement) pp).nestedStatementLeft.equalsTo(l1) && ((ImpliesStatement) pp).nestedStatementRight.equalsTo(l2)) { 
                                    addToProblemList(new Integer(ipp).toString(), "✔");
                                    return true;
                                } else { 
                                    addToProblemList(new Integer(ipp).toString(), "This line should be produced by the provided two lines. ");
                                    return false;
                                }
                            } else { 
                                addToProblemList(new Integer(ipp).toString(), "This line should be a impliesStatement. ");
                                return false;
                            }
                        } else { 
                            addToProblemList(new Integer(ipp).toString(), "The provided line's rule shoud be assume. ");
                            return false;
                        }
                    } else { 
                        addToProblemList(new Integer(ipp).toString(), "The provided lines should be the boxs and boxc for the same box. ");
                        return false; 
                    }
                } else { 
                    addToProblemList(new Integer(ipp).toString(), "The provided line should be boxs. ");
                    return false; 
                }
            } else { 
                addToProblemList(new Integer(ipp).toString(), "The provided lines should be boxStarting line. ");
                return false;
            }
        } else { 
            addToProblemList(new Integer(ipp).toString(), "The provided lines should not be givenLine. ");
            return false;
        }
    }
    
    private boolean checkNotI(int ipp, ProveLine p, int i1, int i2) throws Exception { 
        if(i1 > givenLineNum && i2 >givenLineNum) { 
            ProveLine p1 = items.get(i1-givenLineNum-1); 
            ProveLine p2 = items.get(i2-givenLineNum-1);
            if(p.getNum()-1 == i2 &&  p2 instanceof boxClosingLine) { 
                if(p1 instanceof boxStartingLine) { 
                    if(((boxClosingLine) p2).getStartLine().equals((boxStartingLine)p1)) { 
                        if(p1.getRule()=="ass") { 
                            LogicStatement l1 = stringToLS(findLineToString(i1)); 
                            LogicStatement l2 = stringToLS(findLineToString(i2)); 
                            LogicStatement pp = stringToLS(p.getFml().getText());
                            if(pp instanceof NotStatement) { 
                                if(l2 instanceof Falsity) { 
                                    if(pp.equalsTo(new NotStatement(l1))) { 
                                        addToProblemList(new Integer(ipp).toString(), "✔");
                                        return true;
                                    } else { 
                                        addToProblemList(new Integer(ipp).toString(), "This line should be a notStatement produced by the first line. ");
                                        return false;
                                    }
                                } else { 
                                    addToProblemList(new Integer(ipp).toString(), "The second line should be falsity. ");
                                    return false; 
                                }
                            } else { 
                                addToProblemList(new Integer(ipp).toString(), "This line should be a notStatement. ");
                                return false; 
                            }
                        } else { 
                            addToProblemList(new Integer(ipp).toString(), "The given line's rule shoud be assume. ");
                            return false;
                        }
                    } else { 
                        addToProblemList(new Integer(ipp).toString(), "The provided lines should be the boxs and boxc for the same box. ");
                        return false; 
                    }
                } else { 
                    addToProblemList(new Integer(ipp).toString(), "The provided line should be boxs. ");
                    return false; 
                }
            } else if(p.getNum()-1 == i1 &&  p1 instanceof boxClosingLine) { 
                if(p2 instanceof boxStartingLine) { 
                    if(((boxClosingLine) p1).getStartLine().equals((boxStartingLine)p2)) { 
                        if(p2.getRule()=="ass") { 
                            LogicStatement l1 = stringToLS(findLineToString(i2)); 
                            LogicStatement l2 = stringToLS(findLineToString(i1)); 
                            LogicStatement pp = stringToLS(p.getFml().getText());
                            if(pp instanceof NotStatement) { 
                                if(l2 instanceof Falsity) { 
                                    if(pp.equalsTo(new NotStatement(l1))) { 
                                        addToProblemList(new Integer(ipp).toString(), "✔");
                                        return true;
                                    } else { 
                                        addToProblemList(new Integer(ipp).toString(), "This line should be a notStatement produced by the first line. ");
                                        return false;
                                    }
                                } else { 
                                    addToProblemList(new Integer(ipp).toString(), "The second line should be falsity. ");
                                    return false; 
                                }
                            } else { 
                                addToProblemList(new Integer(ipp).toString(), "This line should be a notStatement. ");
                                return false; 
                            }
                        } else { 
                            addToProblemList(new Integer(ipp).toString(), "The given line's rule shoud be assume. ");
                            return false;
                        }
                    } else { 
                        addToProblemList(new Integer(ipp).toString(), "The provided lines should be the boxs and boxc for the same box. ");
                        return false; 
                    }
                } else { 
                    addToProblemList(new Integer(ipp).toString(), "The provided line should be boxs. ");
                    return false; 
                }
            } else { 
                addToProblemList(new Integer(ipp).toString(), "The provided lines should be boxStarting line. ");
                return false;
            }
        } else { 
            addToProblemList(new Integer(ipp).toString(), "The provided lines should not be givenLine. ");
            return false;
        }
    }
    
    private boolean checkOrE(int ipp, ProveLine pl, int i1, int i2, int i3, int i4, int i5) throws Exception { 
        addToProblemList(new Integer(ipp).toString(), "Please put this rule in the way that taught in the notes, otherwise it will report error. "); 
        if(i2 > givenLineNum && i3 >givenLineNum &&i4 > givenLineNum && i5 >givenLineNum) { 
            
            ProveLine p2 = items.get(i2-givenLineNum-1);
            ProveLine p3 = items.get(i3-givenLineNum-1); 
            ProveLine p4 = items.get(i4-givenLineNum-1);
            ProveLine p5 = items.get(i5-givenLineNum-1);
            if(p2 instanceof TwoBoxStartingLine && p3 instanceof TwoBoxClosingLine && p4 instanceof TwoBoxStartingLine && p5 instanceof TwoBoxClosingLine) { 
                if(((TwoBoxStartingLine) p2).getFirst() && ((TwoBoxStartingLine) p2).getEndLine().equals(p3) && !((TwoBoxStartingLine) p4).getFirst() && ((TwoBoxStartingLine) p4).getEndLine().equals(p5)) { 
                    
                        LogicStatement l1 = stringToLS(findLineToString(i1)); 
                        LogicStatement l2 = stringToLS(findLineToString(i2)); 
                        LogicStatement l3 = stringToLS(findLineToString(i3)); 
                        LogicStatement l4 = stringToLS(findLineToString(i4)); 
                        LogicStatement l5 = stringToLS(findLineToString(i5)); 
                        LogicStatement pp = stringToLS(pl.getFml().getText());
                        if(l1 instanceof OrStatement) { 
                            if(p2.getRule()=="ass" && p4.getRule()=="ass") { 
                                if(((OrStatement) l1).nestedStatementLeft.equalsTo(l2) && ((OrStatement) l1).nestedStatementRight.equalsTo(l4) || ((OrStatement) pp).nestedStatementRight.equalsTo(l2) && ((OrStatement) pp).nestedStatementLeft.equalsTo(l4)) { 
                                    if(l3.equalsTo(l5) && l5.equalsTo(pp)) { 
                                        addToProblemList(new Integer(ipp).toString(), "✔");
                                        return true;
                                    } else { 
                                        addToProblemList(new Integer(ipp).toString(), "The third and fifth provided lines should be same as this line. ");
                                        return false; 
                                    }
                                } else { 
                                    addToProblemList(new Integer(ipp).toString(), "The second and fourth provided lines should be able to produce the first provided line. ");
                                    return false; 
                                }
                            } else { 
                                addToProblemList(new Integer(ipp).toString(), "The second provided line and the fourth provided line's rule should be assume. ");
                                return false; 
                            }
                        } else { 
                            addToProblemList(new Integer(ipp).toString(), "The first provided line should be an OrStatement. ");
                            return false; 
                        }
                } else { 
                    addToProblemList(new Integer(ipp).toString(), "The 2nd and 3rd provided lines should be in the first part of twobox and vice versa. ");
                    return false; 
                }
            } else { 
                addToProblemList(new Integer(ipp).toString(), "The provided lines shoud be the starting and closing lines in a twobox. ");
                return false;
            }
        } else { 
            addToProblemList(new Integer(ipp).toString(), "The provided lines should not be givenLine. ");
            return false;
        }
    }
    
    private boolean checkPC(int ipp, LogicStatement pp, LogicStatement p1, LogicStatement p2) { 
        if(p2 instanceof Falsity) { 
            if(pp.equalsTo(new NotStatement(p1)) || p1.equalsTo(new NotStatement(pp))) { 
                addToProblemList(new Integer(ipp).toString(), "✔");
                return true;
            } else { 
                addToProblemList(new Integer(ipp).toString(), "This line should be contract to one of the provided line. ");
                return false;
            }
        } else if (p1 instanceof Falsity) { 
            if(pp.equalsTo(new NotStatement(p2)) || p2.equalsTo(new NotStatement(pp))) { 
                addToProblemList(new Integer(ipp).toString(), "✔");
                return true;
            } else { 
                addToProblemList(new Integer(ipp).toString(), "This line should be contract to one of the provided line. ");
                return false;
            }
        } else { 
            addToProblemList(new Integer(ipp).toString(), "One of the provided lines should be falsity. ");
            return false; 
        } 
        
    }
    
    private boolean checkAss(int ipp, ProveLine pl) { 
        if(pl instanceof boxStartingLine) { 
            addToProblemList(new Integer(ipp).toString(), "✔");
            return true; 
        } else { 
            addToProblemList(new Integer(ipp).toString(), "This line should should be a boxStartingLine. "); 
            return false; 
        }
    }
    
    private boolean checkTick(int ipp, LogicStatement ls, LogicStatement a) { 
        if(ls.equalsTo(a)) { 
            addToProblemList(new Integer(ipp).toString(), "✔");
            return true; 
        } else {  
            addToProblemList(new Integer(ipp).toString(), "This line should be same as the provided line. "); 
            return false; 
        }
    }
    
    private boolean checkLemma(int ipp, LogicStatement ls) { 
        if(ls instanceof OrStatement) { 
            if(((OrStatement) ls).nestedStatementLeft.equalsTo(new NotStatement(((OrStatement) ls).nestedStatementRight)) || ((OrStatement) ls).nestedStatementRight.equalsTo(new NotStatement(((OrStatement) ls).nestedStatementLeft))) { 
                addToProblemList(new Integer(ipp).toString(), "✔");
                return true; 
            } else { 
                addToProblemList(new Integer(ipp).toString(), "The left part should be the oppsited of the right part or vise versa. ");
                return false; 
            } 
        } else { 
            addToProblemList(new Integer(ipp).toString(), "This line should be an Orstatement. "); 
            return false; 
        }
    }
    
    @FXML 
    public void cancelButtonAction(ActionEvent event) { 
        
        items.removeAll(items);
        currentMaxLine=givenLineNum;
        
    }
    
    private int getCurrentFocus() { 
        int idx = proveView.getSelectionModel().getSelectedIndex(); 
        return idx;
    }
    
    @FXML 
    public void deleteButtonAction(ActionEvent event) throws Exception{ 
        int idx = getCurrentFocus();
        
        if (idx != -1) {
            ProveLine pl = items.get(idx);
            deleteIdx(pl);
            
        }
        
    }
    
    private void deleteIdx(ProveLine pl) { 
            int idx = pl.getNum()-givenLineNum-1;
            System.out.println("deleting number "+ pl.getNum());
            List<ProveLine> below = new ArrayList<ProveLine>(); 
        
            if(idx != items.size()) {
                for(int j = idx+1; j<items.size(); j++) { 
                    below.add(items.get(j));
                }
            }
            if(pl instanceof TwoBoxStartingLine) { 
                deleteTwoBS(pl, below, idx);
                
            } else if(pl instanceof TwoBoxClosingLine) {  
                deleteTwoBC(pl, below, idx);
            } else if(pl instanceof boxStartingLine) { 
                deleteBS(pl, below, idx);
            } else if(pl instanceof boxClosingLine) { 
                deleteBC(pl, below, idx);
            } else { 
                deleteL(pl, below, idx);
            }
           
    }
    
    
    
    private void deleteTwoBS(ProveLine pl, List<ProveLine> below, int idx) { 
        if(((TwoBoxStartingLine) pl).getFirst()) { 
            System.out.println(((boxStartingLine) pl).getLineInBox().size());
            if(((boxStartingLine) pl).getLineInBox().size() == 2 && ((TwoBoxStartingLine) pl).getPair().getLineInBox().size() == 2) { 
                if(pl.isInBox()) { 
                            findParentBox(pl).deleteLineInBox(pl);
                            findParentBox(pl).deleteLineInBox(((boxStartingLine) pl).getEndLine());
                            findParentBox(pl).deleteLineInBox(((TwoBoxStartingLine) pl).getPair());
                            findParentBox(pl).deleteLineInBox(((TwoBoxStartingLine) pl).getPair().getEndLine());
                    for(boxStartingLine bs: findParentBox(pl).getParentBox()) { 
                            
                            bs.deleteLineInBox(pl);
                            bs.deleteLineInBox(((boxStartingLine) pl).getEndLine());
                            bs.deleteLineInBox(((TwoBoxStartingLine) pl).getPair());
                            bs.deleteLineInBox(((TwoBoxStartingLine) pl).getPair().getEndLine());
                    }
                    
                }
                    //remove the selected pl
                    items.remove(pl);
                    items.remove(((boxStartingLine) pl).getEndLine());
                    items.remove(((TwoBoxStartingLine) pl).getPair()); 
                    items.remove(((TwoBoxStartingLine) ((TwoBoxStartingLine) pl).getPair()).getEndLine());
                    //reassign all numbers from the point
                    reAssignAll(idx);
                    //remove legalargs for all the existing pls
                    for(ProveLine plpl:items) { 
                        plpl.getLegalArgs().removeAll(plpl.getLegalArgs());
                    }
                    //update legalargs for all the existing pls
                    for(ProveLine plpl:items) { 
                        updateLegalArgs(plpl);
                    }
                    
                    currentMaxLine-=4;
                } 
        }
    }
    
    private void deleteTwoBC(ProveLine pl, List<ProveLine> below, int idx) { 
        
    }
    
    private void deleteBS(ProveLine pl, List<ProveLine> below, int idx) {
        System.out.println(((boxStartingLine) pl).getLineInBox());
            if(((boxStartingLine) pl).getLineInBox().size() == 2) { 
                //remove the selected pl
                if(pl.isInBox()) { 
                            findParentBox(pl).deleteLineInBox(pl);
                            findParentBox(pl).deleteLineInBox(((boxStartingLine) pl).getEndLine());
                    for(boxStartingLine bs: findParentBox(pl).getParentBox()) { 
                            bs.deleteLineInBox(pl);
                            bs.deleteLineInBox(((boxStartingLine) pl).getEndLine());
                    }
                    
                }
                items.remove(pl);
                items.remove(((boxStartingLine) pl).getEndLine());
                //reassign all numbers from the point
                reAssignAll(idx);
                //remove legalargs for all the existing pls
                for(ProveLine plpl:items) { 
                    plpl.getLegalArgs().removeAll(plpl.getLegalArgs());
                }
                //update legalargs for all the existing pls
                for(ProveLine plpl:items) { 
                    updateLegalArgs(plpl);
                }
                currentMaxLine-=2;
            }
        
    }
    
    private void deleteBC(ProveLine pl, List<ProveLine> below, int idx) { 
            
    }
    
    private void deleteL(ProveLine pl, List<ProveLine> below, int idx) {
                if(pl.isInBox()) { 
                    findParentBox(pl).deleteLineInBox(pl);
                    for(boxStartingLine bs: findParentBox(pl).getParentBox()) { 
                        bs.deleteLineInBox(pl);
                    }
                    
                }
        //remove the selected pl
                items.remove(pl);
                //reassign all numbers from the point
                reAssignAll(idx);
                //remove legalargs for all the existing pls
                for(ProveLine plpl:items) { 
                    plpl.getLegalArgs().removeAll(plpl.getLegalArgs());
                }
                //update legalargs for all the existing pls
                for(ProveLine plpl:items) { 
                    updateLegalArgs(plpl);
                }
                currentMaxLine-=1;
    }
    
    private void reAssignAll(int idx) { 
        for(int i = idx; i < items.size();i++) { 
            items.get(i).reAssignNum(i+givenLineNum+1);
        }
    }
    
    @FXML 
    public void createNewLineButton(ActionEvent event) {
        
        
        int selectedLine = proveView.getSelectionModel().getSelectedIndex(); 
        if(selectedLine == -1) { 
            currentMaxLine++;
            ProveLine pl = new ProveLine(currentMaxLine);
            items.add(currentMaxLine-givenLineNum-1, pl);
            updateLegalArgs(pl);
        } else { 
            
            if(!(items.get(selectedLine) instanceof TwoBoxClosingLine && ((TwoBoxClosingLine) items.get(selectedLine)).getStartLine().getFirst())) { 
                currentMaxLine++;
                ProveLine pl = new ProveLine(selectedLine+1+givenLineNum+1); 
                items.add(selectedLine+1, pl);
                reAssignAll(selectedLine);
                assignBox(pl, items.get(selectedLine));
                updateLegalArgs(pl);
            }
        }
    }
    //set in box for this line and update parent boxes' line in box
    private void assignBox(ProveLine pl, ProveLine upper) { 
        if(upper instanceof boxStartingLine || upper.isInBox()) { 
            pl.setInBox();
            findParentBox(pl).addLineInBox(pl);
            for(boxStartingLine bs: findParentBox(pl).getParentBox()) { 
                bs.addLineInBox(pl);
            }
            for(int i = 0; i < findParentBox(pl).getIndent(); i++) { 
                pl.indent();
            }
        }
    }
    //find this one's parentBox
    private boxStartingLine findParentBox(ProveLine pl) { 
        ProveLine upper = items.get(pl.getNum()-givenLineNum-2);
        /*if(upper instanceof TwoBoxClosingLine) { 
            if(((TwoBoxClosingLine) upper).getStartLine().getFirst()) { 
                return 
            }
        }*/
        if(upper instanceof boxClosingLine) { 
            return findParentBox(((boxClosingLine) upper).getStartLine());
        } else if(!(upper instanceof boxStartingLine)) { 
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
        applyRule("✔",1);
    }
    
    @FXML 
    public void applyPC(ActionEvent event) { 
        applyRule("PC", 2);
    }
    
    @FXML 
    public void applyCreateBox(ActionEvent event) {
        
        int selectedLine = proveView.getSelectionModel().getSelectedIndex(); 
        if(selectedLine == -1) { 
            currentMaxLine++;
            boxStartingLine bs = new boxStartingLine(currentMaxLine);
            items.add(currentMaxLine-givenLineNum-1, bs);
            
            currentMaxLine++;
            boxClosingLine bc = new boxClosingLine(currentMaxLine);
            
            bs.setEndLine(bc);
            bc.setStartLine(bs);
            boxes.add(bs);
            
            items.add(currentMaxLine-givenLineNum-1, bc);
            if(!items.get(items.size()-1).equals(bc)) { 
                items.get(currentMaxLine-givenLineNum).addLegalArgs(bs);
                items.get(currentMaxLine-givenLineNum).addLegalArgs(bc);
            }
            
            bs.indent();
            bc.indent();
            ((HBox) bs.getChildren().get(bs.getIndent())).setStyle("-fx-border-color:black white white white;");
            ((HBox) bc.getChildren().get(bs.getIndent())).setStyle("-fx-border-color:white white black white;");
            updateLegalArgs(bs);
            updateLegalArgs(bc);
            
        } else { 
            
            if(!(items.get(selectedLine) instanceof TwoBoxClosingLine && ((TwoBoxClosingLine) items.get(selectedLine)).getStartLine().getFirst())) { 
                currentMaxLine++;
                boxStartingLine bs = new boxStartingLine(selectedLine+1+givenLineNum+1); 
                items.add(selectedLine+1, bs); 
                currentMaxLine++;
                boxClosingLine bc = new boxClosingLine(selectedLine+1+givenLineNum+2);

                bs.setEndLine(bc);
                bc.setStartLine(bs);
                boxes.add(bs);

                items.add(selectedLine+2, bc);
                reAssignAll(selectedLine);
                bs.indent();
                bc.indent();
                assignBoxToBox(bs, bc, items.get(selectedLine)); 

                if(!items.get(items.size()-1).equals(bc)) { 
                    items.get(selectedLine+3).addLegalArgs(bs);
                    items.get(selectedLine+3).addLegalArgs(bc);
                }
                ((HBox) bs.getChildren().get(bs.getIndent())).setStyle("-fx-border-color:black white white white;");
                ((HBox) bc.getChildren().get(bs.getIndent())).setStyle("-fx-border-color:white white black white;");
                updateLegalArgs(bs);
                updateLegalArgs(bc);
            }
            
            
        }
        
    }
    
    private void assignBoxToBox(boxStartingLine bs, boxClosingLine bc, ProveLine upper) { 
        if(upper instanceof boxStartingLine || upper.isInBox()) { 
            
            
            bs.indent();
            bc.indent();
            
            bs.setInBox();
            bc.setInBox();
            
            findParentBox(bs).addSubBoxes(bs);
            bs.addParentBox(findParentBox(bs));
            findParentBox(bs).addLineInBox(bs);
            findParentBox(bs).addLineInBox(bc);
            
            for(boxStartingLine bsl:findParentBox(bs).getParentBox()) { 
                bs.addParentBox(bsl);
                bsl.addLineInBox(bs);
                bsl.addLineInBox(bc);
                bs.indent();
                bc.indent();
            }
        } 
    }
    
    
    @FXML 
    public void applyCreateBoxTwo(ActionEvent event) {
        
        int selectedLine = proveView.getSelectionModel().getSelectedIndex(); 
        if(selectedLine == -1) { 
            currentMaxLine++;
            TwoBoxStartingLine bso = new TwoBoxStartingLine(currentMaxLine);
            items.add(currentMaxLine-givenLineNum-1, bso);
            bso.setFirst();
            currentMaxLine++;
            TwoBoxClosingLine bco = new TwoBoxClosingLine(currentMaxLine);
            
            bso.setEndLine(bco);
            bco.setStartLine(bso);
            boxes.add(bso);
            
            items.add(currentMaxLine-givenLineNum-1, bco);
            
            currentMaxLine++;
            TwoBoxStartingLine bst = new TwoBoxStartingLine(currentMaxLine);
            items.add(currentMaxLine-givenLineNum-1, bst);
            
            currentMaxLine++;
            TwoBoxClosingLine bct = new TwoBoxClosingLine(currentMaxLine);
            
            bst.setEndLine(bct);
            bct.setStartLine(bst);
            boxes.add(bst);
            
            items.add(currentMaxLine-givenLineNum-1, bct);
            
            bst.setPair(bso);
            bso.setPair(bst);
            
            if(!items.get(items.size()-1).equals(bct)) { 
                items.get(currentMaxLine-givenLineNum).addLegalArgs(bso);
                items.get(currentMaxLine-givenLineNum).addLegalArgs(bco);
                items.get(currentMaxLine-givenLineNum).addLegalArgs(bst);
                items.get(currentMaxLine-givenLineNum).addLegalArgs(bct);
            }
            
            bso.indent();
            bco.indent();
            bst.indent();
            bct.indent();
            ((HBox) bso.getChildren().get(bso.getIndent())).setStyle("-fx-border-color:black white white white;");
            ((HBox) bco.getChildren().get(bco.getIndent())).setStyle("-fx-border-color:white white black white;");
            ((HBox) bst.getChildren().get(bst.getIndent())).setStyle("-fx-border-color:black white white white;");
            ((HBox) bct.getChildren().get(bct.getIndent())).setStyle("-fx-border-color:white white black white;");
            updateLegalArgs(bso);
            updateLegalArgs(bco);
            updateLegalArgs(bst);
            updateLegalArgs(bct);
        } else { 
            
            if(!(items.get(selectedLine) instanceof TwoBoxClosingLine && ((TwoBoxClosingLine) items.get(selectedLine)).getStartLine().getFirst())) { 
                currentMaxLine++;
                TwoBoxStartingLine bso = new TwoBoxStartingLine(selectedLine+1+givenLineNum+1); 
                items.add(selectedLine+1, bso); 
                bso.setFirst();
                currentMaxLine++;
                TwoBoxClosingLine bco = new TwoBoxClosingLine(selectedLine+1+givenLineNum+2);

                bso.setEndLine(bco);
                bco.setStartLine(bso);
                boxes.add(bso);

                items.add(selectedLine+2, bco);
                currentMaxLine++;
                TwoBoxStartingLine bst = new TwoBoxStartingLine(selectedLine+1+givenLineNum+1); 
                items.add(selectedLine+3, bst); 
                currentMaxLine++;
                TwoBoxClosingLine bct = new TwoBoxClosingLine(selectedLine+1+givenLineNum+2);

                bst.setEndLine(bct);
                bct.setStartLine(bst);
                boxes.add(bst);

                items.add(selectedLine+4, bct);

                reAssignAll(selectedLine);

                bst.setPair(bso);
                bso.setPair(bst);

                bso.indent();
                bco.indent();
                bst.indent();
                bct.indent();
                ((HBox) bso.getChildren().get(bso.getIndent())).setStyle("-fx-border-color:black white white white;");
                ((HBox) bco.getChildren().get(bco.getIndent())).setStyle("-fx-border-color:white white black white;");
                ((HBox) bst.getChildren().get(bst.getIndent())).setStyle("-fx-border-color:black white white white;");
                ((HBox) bct.getChildren().get(bct.getIndent())).setStyle("-fx-border-color:white white black white;");
                assignBoxToBox(bso, bco, items.get(selectedLine)); 
                assignBoxToBox(bst, bct, items.get(selectedLine)); 
                if(!items.get(items.size()-1).equals(bct)) { 
                    items.get(selectedLine+5).addLegalArgs(bso);
                    items.get(selectedLine+5).addLegalArgs(bco);
                    items.get(selectedLine+5).addLegalArgs(bst);
                    items.get(selectedLine+5).addLegalArgs(bct);
                }
                updateLegalArgs(bso);
                updateLegalArgs(bco);
                updateLegalArgs(bst);
                updateLegalArgs(bct);
            }
            
            
        }
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
    
    @FXML 
    public void moveUpButton(ActionEvent event) { 
        int i = getCurrentFocus();
        if(i==-1) { 
            
        } else { 
            ProveLine pl = items.get(i);
            if(!checkIfFirstLine(pl)) { 
                ProveLine upper = items.get(pl.getNum()-givenLineNum-2);
                putTo(pl, upper);
            } 
        }
    }
    
    @FXML 
    public void moveDownButton(ActionEvent event) { 
        int i = getCurrentFocus();
        if(i==-1) { 
            
        } else { 
            ProveLine pl = items.get(i);
            if(!items.get(items.size()-1).equals(pl)) { 
                ProveLine down = items.get(pl.getNum()-givenLineNum);
                putTo(down, pl);
            } 
        }
    }
    
    private void putTo(ProveLine pl, ProveLine plpl) { 
                String tmpS = plpl.getFml().getText();
                plpl.getFml().setText(pl.getFml().getText());
                pl.getFml().setText(tmpS);
                
                if(pl.getRuled() && plpl.getRuled()) { 
                    HBox plhb = new HBox();
                    plhb = plpl.setRulhb(pl.getRulhb()); 
                    pl.setRulhb(plhb); 
                } else if(!pl.getRuled() && plpl.getRuled()) { 
                    HBox plhb = new HBox();
                    plhb.getChildren().addAll(plpl.getRulhb().getChildren());
                    plpl.resetRulhb(); 
                    pl.setRulhb(plhb); 
                    pl.setRuled(); 
                    plpl.setNotRuled();
                } else if(pl.getRuled() && !plpl.getRuled()) { 
                    HBox plhb = new HBox();
                    plhb.getChildren().addAll(pl.getRulhb().getChildren());
                    pl.resetRulhb(); 
                    plpl.setRulhb(plhb); 
                    plpl.setRuled();
                    pl.setNotRuled();
                } else { 
                    
                }
    }
    
}
