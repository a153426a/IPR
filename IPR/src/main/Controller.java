/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import ast.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javafx.application.Platform;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lexerAndParser.*;
/**
 *
 * @author zl2511
 */
public class Controller {
    
    @FXML
    private String[] initStartFormulas; 

    private List<LogicStatement> startFormulas = new ArrayList();
    
    private LogicStatement goalFormula; 
    
    private int textFieldFocus = 0;

    @FXML
    private Button andButton;
    @FXML
    private Button orButton;
    @FXML
    private Button impliesButton;
    @FXML
    private Button notButton;
    @FXML
    private Button truthButton;
    @FXML
    private Button thereesistsButton;
    @FXML
    private Button iffButton;
    @FXML
    private Button equalsButton;
    @FXML
    private Button falsityButton;
    @FXML
    private Button forallButton;
    @FXML
    private Button checkButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextArea startArea;
    @FXML
    private TextField goalArea;
    
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private int caretpos;
    
    public Controller() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("start.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        caretpos = 0;
    }
    
    public void launchController(Stage stage) {
        this.stage = stage;
        stage.setTitle("start");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.hide();
        stage.show();
    }

    @FXML
    public void andButtonAction(ActionEvent event) {
        
        if(textFieldFocus == 1) { 
            goalArea.insertText(caretpos, Symbol.AND.toString());
        } else { 
            //startArea.setText(startArea.getText() + Symbol.AND);
            //startArea.requestFocus();
            startArea.insertText(startArea.getCaretPosition(), Symbol.AND.toString());
        }
        
        
    }
    
    @FXML
    public void orButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            goalArea.insertText(caretpos, Symbol.OR.toString());
        } else { 
            startArea.insertText(startArea.getCaretPosition(), Symbol.OR.toString());
        }
    }
    
    @FXML
    public void impliesButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            goalArea.insertText(caretpos, Symbol.IMPLIES.toString());
        } else { 
            startArea.insertText(startArea.getCaretPosition(), Symbol.IMPLIES.toString());
        }
    }
    
    @FXML
    public void notButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            goalArea.insertText(caretpos, Symbol.NOT.toString());
        } else { 
            startArea.insertText(startArea.getCaretPosition(), Symbol.NOT.toString());
        }
    }
    
    @FXML
    public void truthButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            goalArea.insertText(caretpos, Symbol.TRUTH.toString());
        } else { 
            startArea.insertText(startArea.getCaretPosition(), Symbol.TRUTH.toString());
        }
    }
    
    @FXML
    public void thereesistsButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            goalArea.insertText(caretpos, Symbol.THEREEXISTS.toString());
        } else { 
            startArea.insertText(startArea.getCaretPosition(), Symbol.THEREEXISTS.toString());
        }
    }
    
    @FXML
    public void iffButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            goalArea.insertText(caretpos, Symbol.IFF.toString());
        } else { 
            startArea.insertText(startArea.getCaretPosition(), Symbol.IFF.toString());
        }
    }
    
    @FXML
    public void equalsButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            goalArea.insertText(caretpos, Symbol.EQUALS.toString());
        } else { 
            startArea.insertText(startArea.getCaretPosition(), Symbol.EQUALS.toString());
        }
    }
    
    @FXML
    public void falsityButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            
            goalArea.insertText(caretpos, Symbol.FALSITY.toString());
        } else { 
            startArea.insertText(startArea.getCaretPosition(), Symbol.FALSITY.toString());
        }
    }
    
    @FXML
    public void forallButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            goalArea.insertText(caretpos, Symbol.FORALL.toString());
        } else { 
            startArea.insertText(startArea.getCaretPosition(), Symbol.FORALL.toString());
        }
    }
    @FXML 
    public void goalRelease(KeyEvent event) { 
        caretpos = goalArea.getCaretPosition();
    }
    @FXML
    public void checkButtonAction(ActionEvent event) throws Exception {
        InputStream is;
        
        Scanner sc = new Scanner(startArea.getText());
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            if (line.isEmpty())
                continue;
            initStartFormulas = line.split("\\s");
            for (String token : initStartFormulas) {
                if (token.isEmpty()) {
                    continue;
                } else { 
                    try {
                        is = new ByteArrayInputStream(token.getBytes("UTF-8"));
                        Lexer l = new Lexer(is);
                        parser p = new parser(l);
                        LogicStatement ls = (LogicStatement) p.parse().value;
                        startFormulas.add(ls);

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
            
        }
        
        try {
            if(!goalArea.getText().isEmpty()) { 
                
                is = new ByteArrayInputStream(goalArea.getText().getBytes("UTF-8"));
                Lexer l = new Lexer(is);
                parser p = new parser(l);
                goalFormula = (LogicStatement) p.parse().value;
                
                
                
                Node  currentSource = (Node)  event.getSource(); 
                Stage currentStage = (Stage) currentSource.getScene().getWindow();
                Prove prove = new Prove(); 
                prove.redirectprove(currentStage, startFormulas, goalFormula);
            } else { 
                
                System.out.println("You should input a goal formula");
                
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //TODO 
        //Transfer value is a shit thing to do. 
        
        
        /*
        Parent root2 = FXMLLoader.load(getClass().getResource("proveWindow.fxml"));
        Stage proveStage = new Stage();
        Scene scene = new Scene(root2);
        
        proveStage.setScene(scene);
        proveStage.show();
        */
        
        //Close the current stage
        
        //currentStage.close();
        
        
        
    }
    
   
    
    @FXML
    public void cancelButtonAction(ActionEvent event) {
        startArea.setText("");
        goalArea.setText("");
    }
    
    @FXML
    public void setFocusZero() { 
        textFieldFocus = 0; 
        caretpos = startArea.getCaretPosition();
    }
    
    @FXML
    public void setFocusOne() { 
        textFieldFocus = 1; 
        caretpos = goalArea.getCaretPosition();
    }
    
    @FXML //TODO
    public boolean checkTable(List<LogicStatement> ll, LogicStatement l) {
        List<String> variables = new ArrayList(); 
        for (LogicStatement str: ll) {
            addLists(variables, grabVariable(str));
        }
        addLists(variables, grabVariable(l)); 
        int[][] truthTable = new int[(int)Math.pow(2, variables.size())][variables.size()];
        int truth = 0;
        for (int i = 0; i < variables.size(); i++) { 
            int divider = (int)Math.pow(2, i);
            for (int j = 0; j < Math.pow(2, variables.size()); j++) { 
                if(j % divider != 0) { 
                    truthTable[j][i] = truth; 
                } else { 
                    truth = flip(truth);
                    truthTable[j][i] = truth;
                }
            }
        }
        
        return true;
    }
    
    @FXML 
    public int flip(int i) { 
        if (i == 1) { 
            i = 0; 
        } else { 
            i = 1;
        }
        return i;
    }
    
    @FXML 
    public List<String> grabVariable(LogicStatement l) { 
        List<String> result = new ArrayList();
        if (l instanceof Variable) { 
            result.add(((Variable)l).name);
        } else if (l instanceof BinaryOpStatement) { 
            LogicStatement left = ((BinaryOpStatement) l).nestedStatementLeft;
            LogicStatement right = ((BinaryOpStatement) l).nestedStatementRight;
            addLists(result, grabVariable(left));
            addLists(result, grabVariable(right));
        } else if(l instanceof UnaryOpStatement) { 
            LogicStatement lll = ((UnaryOpStatement) l).nestedStatement;
            addLists(result, grabVariable(lll));
        }
        
        return result;
    }
    
    @FXML 
    public void addLists(List<String> l1, List<String> l2) {
        for (String str: l2) { 
            if (!l1.contains(str)) {
                l1.add(str);
            }
        }
        
    }
    
    
    
}
