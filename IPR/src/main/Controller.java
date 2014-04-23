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
import javafx.stage.Stage;
import lexerAndParser.*;
/**
 *
 * @author zl2511
 */
public class Controller implements Initializable {
    
    @FXML
    public String[] initStartFormulas; 

    public List<LogicStatement> startFormulas = new ArrayList();
    
    public LogicStatement goalFormula; 
    
    public int textFieldFocus = 0;

    @FXML
    public Button andButton;
    @FXML
    public Button orButton;
    @FXML
    public Button impliesButton;
    @FXML
    public Button notButton;
    @FXML
    public Button truthButton;
    @FXML
    public Button thereesistsButton;
    @FXML
    public Button iffButton;
    @FXML
    public Button equalsButton;
    @FXML
    public Button falsityButton;
    @FXML
    public Button forallButton;
    @FXML
    public Button checkButton;
    @FXML
    public Button cancelButton;
    @FXML
    public TextArea startArea;
    @FXML
    public TextField goalArea;

    @FXML
    public void andButtonAction(ActionEvent event) {
        
        if(textFieldFocus == 1) { 
            goalArea.setText(goalArea.getText() + Symbol.AND);
            goalArea.requestFocus();
        } else { 
            startArea.setText(startArea.getText() + Symbol.AND);
            startArea.requestFocus();
        }
        
    }
    
    @FXML
    public void orButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            goalArea.setText(goalArea.getText() + Symbol.OR);
            goalArea.requestFocus();
        } else { 
            startArea.setText(startArea.getText() + Symbol.OR);
            startArea.requestFocus();
        }
    }
    
    @FXML
    public void impliesButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            goalArea.setText(goalArea.getText() + Symbol.IMPLIES);
            goalArea.requestFocus();
        } else { 
            startArea.setText(startArea.getText() + Symbol.IMPLIES);
            startArea.requestFocus();
        }
    }
    
    @FXML
    public void notButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            goalArea.setText(goalArea.getText() + Symbol.NOT);
            goalArea.requestFocus();
        } else { 
            startArea.setText(startArea.getText() + Symbol.NOT);
            startArea.requestFocus();
        }
    }
    
    @FXML
    public void truthButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            goalArea.setText(goalArea.getText() + Symbol.TRUTH);
            goalArea.requestFocus();
        } else { 
            startArea.setText(startArea.getText() + Symbol.TRUTH);
            startArea.requestFocus();
        }
    }
    
    @FXML
    public void thereesistsButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            goalArea.setText(goalArea.getText() + Symbol.THEREEXISTS);
            goalArea.requestFocus();
        } else { 
            startArea.setText(startArea.getText() + Symbol.THEREEXISTS);
            startArea.requestFocus();
        }
    }
    
    @FXML
    public void iffButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            goalArea.setText(goalArea.getText() + Symbol.IFF);
            goalArea.requestFocus();
        } else { 
            startArea.setText(startArea.getText() + Symbol.IFF);
            startArea.requestFocus();
        }
    }
    
    @FXML
    public void equalsButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            goalArea.setText(goalArea.getText() + Symbol.PREDICATE);
            goalArea.requestFocus();
        } else { 
            startArea.setText(startArea.getText() + Symbol.PREDICATE);
            startArea.requestFocus();
        }
    }
    
    @FXML
    public void falsityButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            goalArea.setText(goalArea.getText() + Symbol.FALSITY);
            goalArea.requestFocus();
        } else { 
            startArea.setText(startArea.getText() + Symbol.FALSITY);
            startArea.requestFocus();
        }
    }
    
    @FXML
    public void forallButtonAction(ActionEvent event) {
        if(textFieldFocus == 1) { 
            goalArea.setText(goalArea.getText() + Symbol.FORALL);
            goalArea.requestFocus();
        } else { 
            startArea.setText(startArea.getText() + Symbol.FORALL);
            startArea.requestFocus();
        }
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
            try {
                is = new ByteArrayInputStream(goalArea.getText().getBytes("UTF-8"));
                Lexer l = new Lexer(is);
                parser p = new parser(l);
                goalFormula = (LogicStatement) p.parse().value;
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        //TODO 
        //Transfer value is a shit thing to do. 
        System.out.println(startFormulas.toString());
        System.out.println(goalFormula.toString());
        
        //Node  currenSource = (Node)  event.getSource(); 
        //Stage currentStage = (Stage) currenSource.getScene().getWindow();
        
        ProveController prove = new ProveController(); 
        //prove.redirectprove(currentStage, startFormulas, goalFormula);
        
        
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
    }
    
    @FXML
    public void setFocusOne() { 
        textFieldFocus = 1; 
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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
}
