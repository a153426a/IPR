/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author zl2511
 */
public class GivenLine extends HBox { 
    
    private int number; 
    private String formula; 
    private String rule; 
    private Label num;
    private Label rul;

    
    public GivenLine(int number, String formula, String rule) { 
        
        this.number = number; 
        this.formula = formula; 
        this.rule = rule; 
        num = new Label(new Integer(number).toString());
        //Label num = new Label(new Integer(number).toString());
        Label fml = new Label(formula);
        rul = new Label(rule);
        num.setPrefWidth(50);
        fml.setPrefWidth(450);
        rul.setPrefWidth(200);
        this.getChildren().add(num);
        this.getChildren().add(fml);
        this.getChildren().add(rul);
        
        this.setAlignment(Pos.CENTER);
        
    }
    
    public int getNum() { 
        
        return number;
        
    }
    
    public String getfml() {
        
        return formula;
        
    }
    
    public String getrule() {
        
        return rule; 
        
    }
    
    public void setNum(int i) {
        
        num.setText(new Integer(i).toString());
        
    }
    
    public void setRule(String s) { 
        
        rul.setText(s);
        
    }
    
}
