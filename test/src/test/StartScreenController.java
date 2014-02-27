/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import ast.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import lexerAndParser.parser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lexerAndParser.Lexer;
/**
 *
 * @author zl2511
 */
public class StartScreenController implements Initializable {
    
    @FXML
    public String[] initStartFormulas; 
    public List<LogicStatement> startFormulas = new ArrayList();
    public LogicStatement goalFormula;
    public int textFieldFocus = 0;
    public Button andButton;
    public Button orButton;
    public Button impliesButton;
    public Button notButton;
    public Button truthButton;
    public Button thereesistsButton;
    public Button iffButton;
    public Button equalsButton;
    public Button falsityButton;
    public Button forallButton;
    public Button checkButton;
    public Button cancelButton;
    public TextArea startArea;
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
            goalArea.setText(goalArea.getText() + Symbol.EQUALS);
            goalArea.requestFocus();
        } else { 
            startArea.setText(startArea.getText() + Symbol.EQUALS);
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
    public void checkButtonAction(ActionEvent event) {
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
                System.out.println(startFormulas.toString());
                checkTable(startFormulas, goalFormula);
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
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
    
    @FXML
    public boolean checkTable(List<LogicStatement> ll, LogicStatement l) {
        List<String> variables = new ArrayList(); 
        for (LogicStatement str: ll) {
            addLists(variables, grabVariable(str));
        }
        addLists(variables, grabVariable(l)); 
        System.out.println(variables);
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
        
        boolean t = true; 
        boolean f = false; 
        System.out.println(t);
        System.out.println(f);
        System.out.println(t&f);
        System.out.println(t|f);
        //TODO
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
    
    @FXML void addLists(List<String> l1, List<String> l2) {
        for (String str: l2) { 
            if (!l1.contains(str)) {
                l1.add(str);
            }
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}
