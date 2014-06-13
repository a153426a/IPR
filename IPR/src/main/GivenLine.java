/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.Serializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author zl2511
 */
public class GivenLine extends HBox implements Serializable {

    private int number;
    private String formula;
    private String rule;
    private Label num;
    private Label rul;
    private Label fml;

    public GivenLine(int number, String formula, String rule) {
        HBox hb = new HBox();
        this.number = number;
        this.formula = formula;
        this.rule = rule;
        num = new Label(new Integer(number).toString());
        //Label num = new Label(new Integer(number).toString());
        fml = new Label(formula);
        rul = new Label(rule);
        num.setPrefWidth(50);
        fml.setPrefWidth(250);
        //rul.setPrefWidth(200);
        hb.getChildren().add(num);
        hb.getChildren().add(fml);
        hb.getChildren().add(rul);

        hb.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().add(hb);

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
    
    public void updateString(String s) { 
        setFormula(s);
        setFml(s);
    }
    
    public void setFormula(String s) { 
        formula = s;
    }
    
    public void setFml(String s) { 
        fml.setText(s);
    }

}
