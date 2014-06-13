/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.event.MouseAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 *
 * @author zl2511
 */
public class ProveLine extends HBox implements Serializable {

    private HBox hb;
    private Label num;
    private TextField fml;
    private HBox rulhb;
    private String ruleName;
    private TextField[] arguments;
    private boolean haveArgu;
    private boolean ruled;
    private int focus;
    private int caretIndex;
    private List<HBox> legalArgs;
    private boolean inBox;
    private int indentTime;

    public ProveLine(int number) {
        this.hb = new HBox();
        num = new Label();
        fml = new TextField();
        focus = 1;
        caretIndex = 0;
        rulhb = new HBox();
        inBox = false;
        legalArgs = new ArrayList<HBox>();
        indentTime = 0;
        fml.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if (newValue) {
                    focus = 1;
                }
            }
        });

        fml.addEventFilter(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent m) {
                        caretIndex = fml.getCaretPosition();
                    }
                ;
        });
        
        fml.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                caretIndex = fml.getCaretPosition();
            }

        });

        num.setText(new Integer(number).toString());
        num.setPrefWidth(50);
        fml.setPrefWidth(250);

        hb.getChildren().add(num);
        hb.getChildren().add(fml);
        hb.getChildren().add(rulhb);

        hb.setAlignment(Pos.CENTER_LEFT);
        rulhb.setAlignment(Pos.CENTER_LEFT);
        rulhb.setPrefWidth(400);
        this.getChildren().add(hb);
        this.setAlignment(Pos.CENTER_LEFT);

        haveArgu = false;
        ruled = false;
    }

    public int getNum() {

        return Integer.parseInt(num.getText());

    }

    public TextField getFml() {

        return fml;

    }

    public String getRule() {

        return ruleName;

    }

    public void setRule(String s, int argumentNum) {

        ruleName = s;
        Label ruleNameLabel = new Label();

        if (argumentNum == 0) {
            ruleNameLabel.setText(s);
            rulhb.getChildren().add(ruleNameLabel);
            haveArgu = false;
        } else {
            haveArgu = true;
            arguments = new TextField[argumentNum];
            Label endBracketLabel = new Label();

            //ruleNameLabel.setPrefWidth(40);
            endBracketLabel.setPrefWidth(20);

            ruleNameLabel.setText(s + " ( ");
            endBracketLabel.setText(" ) ");

            rulhb.getChildren().add(ruleNameLabel);

            for (int i = 0; i < argumentNum; i++) {
                final int currentItem = i;
                if (i == argumentNum - 1) {
                    final int p = i;
                    TextField tf = new TextField();

                    tf.setPrefWidth(40);

                    getRulhb().getChildren().add(tf);
                    arguments[p] = tf;

                    arguments[p].focusedProperty().addListener(new ChangeListener<Boolean>() {

                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            if (newValue) {
                                focus = currentItem + 2;
                            }
                        }

                    });

                    arguments[p].addEventFilter(MouseEvent.MOUSE_CLICKED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent m) {
                                    caretIndex = arguments[p].getCaretPosition();
                                }
                            ;
                    });
        
                    arguments[p].addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

                        @Override
                        public void handle(KeyEvent event) {
                            caretIndex = arguments[p].getCaretPosition();
                        }

                    });

                } else {
                    final int p = i;
                    TextField tf = new TextField();
                    Label l = new Label();

                    l.setText(", ");

                    tf.setPrefWidth(40);
                    l.setPrefWidth(20);

                    getRulhb().getChildren().add(tf);
                    getRulhb().getChildren().add(l);
                    arguments[p] = tf;

                    arguments[p].focusedProperty().addListener(new ChangeListener<Boolean>() {

                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            if (newValue) {
                                focus = currentItem + 2;
                            }
                        }

                    });

                    arguments[p].addEventFilter(MouseEvent.MOUSE_CLICKED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent m) {
                                    caretIndex = arguments[p].getCaretPosition();
                                }
                            ;
                    });
        
                    arguments[p].addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

                        @Override
                        public void handle(KeyEvent event) {
                            caretIndex = arguments[p].getCaretPosition();
                        }

                    });

                }

            }

            rulhb.getChildren().add(endBracketLabel);
        }
    }

    public void removeRule() {
        if (!haveArgu) {
            rulhb.getChildren().remove(rulhb.getChildren().size() - 1);
        } else {
            rulhb.getChildren().remove(0, rulhb.getChildren().size());
        }
    }

    public HBox getRulhb() {

        return rulhb;

    }

    public HBox setRulhb(HBox hb) {
        HBox result = new HBox();
        result.getChildren().addAll(rulhb.getChildren());
        rulhb.getChildren().removeAll(rulhb.getChildren());
        rulhb.getChildren().addAll(hb.getChildren());
        return result;
    }

    public void resetRulhb() {
        rulhb.getChildren().removeAll(rulhb.getChildren());
    }

    public boolean haveArguments() {
        return haveArgu;
    }

    public int[] getArguments() {
        int[] result = new int[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].getText().isEmpty()) {
                result[i] = 0;
            } else {
                try {
                    int a = Integer.parseInt(arguments[i].getText());
                    if(a>0) { 
                        result[i] = a; 
                    } else { 
                        result[i] = 0;
                    }
                } catch (NumberFormatException e) {
                    result[i] = 0;
                }

            }
        }
        return result;
    }

    public boolean emptyArguments() {

        for (TextField tf : arguments) {

            if (tf.getText().equals("")) {
                return true;

            }

        }
        return false;

    }

    public void setRuled() {

        ruled = true;

    }

    public void setNotRuled() {
        ruled = false;
    }

    public boolean getRuled() {

        return ruled;

    }

    public TextField getCurrentTextField() {
        if (focus == 1) {
            return fml;
        } else {

            return arguments[focus - 2];
        }
    }

    public int getCaretIndex() {
        return caretIndex;
    }

    public void reAssignNum(int i) {
        num.setText(new Integer(i).toString());
    }

    public boolean isInBox() {
        return inBox;
    }

    public void setInBox() {
        inBox = true;
    }

    public List<HBox> getLegalArgs() {
        return legalArgs;
    }

    public void addLegalArgs(HBox i) {
        boolean alreadyHas = false;
        for (HBox j : legalArgs) {
            if (j.equals(i)) {
                alreadyHas = true;
            }
        }
        if (!alreadyHas) {
            legalArgs.add(i);
        }
    }

    public void removeLegalArgs(HBox i) {
        if (legalArgs.contains(i)) {
            legalArgs.remove(i);
        }
    }

    public void indent() {
        Label l = new Label();
        l.setPrefWidth(50);
        this.getChildren().add(0, l);
        l.setStyle("-fx-border-color:white black white white;");
        indentTime++;
    }

    public int getIndent() {
        return indentTime;
    }

}
