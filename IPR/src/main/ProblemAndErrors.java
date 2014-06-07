/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author zl2511
 */
public class ProblemAndErrors extends HBox {

    Label labelO;
    Label labelT;

    public ProblemAndErrors(String i, String s) {
        labelO = new Label();
        labelT = new Label();
        labelO.setText(i);
        labelT.setText(s);
        labelO.setPrefWidth(50);
        labelT.setPrefWidth(800);
        this.getChildren().add(labelO);
        this.getChildren().add(labelT);
    }

}
