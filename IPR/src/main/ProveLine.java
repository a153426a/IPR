/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField; 
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 *
 * @author zl2511
 */
public class ProveLine extends VBox {
    
    private HBox hb; 
    private Label num;
    private TextField fml;
    private HBox rulhb;
    private String ruleName;
    private TextField[] arguments; 
    
    public ProveLine(int number) { 
        this.hb = new HBox();
        num = new Label();
        fml = new TextField();
        
        rulhb = new HBox();
        
        num.setText(new Integer(number).toString());
        
        num.setPrefWidth(50);
        fml.setPrefWidth(450);
        
        hb.getChildren().add(num);
        hb.getChildren().add(fml);
        hb.getChildren().add(rulhb);
        
        hb.setAlignment(Pos.CENTER_LEFT);
        rulhb.setAlignment(Pos.CENTER_LEFT);
        
        this.getChildren().add(hb);
        this.setAlignment(Pos.CENTER);
        
    }
    
    public int getNum() { 
        
        return Integer.parseInt(num.getText());
        
    }
    
    public String getFml() {
        
        return fml.getText();
        
    }
    
    public String getRule() {
        
        return ruleName; 
        
    }
    
    public void setRule(String s, int argumentNum) {
        
        ruleName = s;
        Label ruleNameLabel = new Label();
        
        if(argumentNum == 0) {
            ruleNameLabel.setText(s);
            rulhb.getChildren().add(ruleNameLabel);
        } else {
            arguments = new TextField[argumentNum];
            Label endBracketLabel = new Label();


            //ruleNameLabel.setPrefWidth(40);
            endBracketLabel.setPrefWidth(20);

            ruleNameLabel.setText(s+" ( ");
            endBracketLabel.setText(" ) ");


            rulhb.getChildren().add(ruleNameLabel); 

            for (int i = 0; i < argumentNum; i++) { 

                if(i==argumentNum-1) { 

                    TextField tf = new TextField();

                    tf.setPrefWidth(40); 

                    this.getRulhb().getChildren().add(tf);
                    this.arguments[i] = tf;

                } else { 

                    TextField tf = new TextField();
                    Label l = new Label(); 

                    l.setText(", "); 

                    tf.setPrefWidth(40); 
                    l.setPrefWidth(20);

                    this.getRulhb().getChildren().add(tf);
                    this.getRulhb().getChildren().add(l); 
                    this.arguments[i] = tf;

                }

            }

            rulhb.getChildren().add(endBracketLabel);
        }
    }
    
    public HBox getRulhb() { 
        
        return rulhb;
        
    }
    
    public int[] getArguments() {
        int[] result = new int[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            
            result[i] = Integer.parseInt(arguments[i].getText());
            
        }
        return result;
    }
    
    public boolean emptyArguments() { 
        
        for (TextField tf:arguments) {
            
            if (tf.getText().equals("")) {
                System.out.println("it is empty");
                return true; 
                
            }
            
        }
        System.out.println("it is not empty");
        return false;
        
    }
    
}
