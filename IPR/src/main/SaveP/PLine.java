/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.SaveP;

/**
 *
 * @author zl2511
 */
public class PLine extends PNode {
    private String fml;
    private boolean ruled;
    private String rule;
    private boolean hasArgument;
    private int[] arguments;
    public PLine() { 
        size = 1;
    }
    
    public String getFml() {
        return fml;
    }

    public boolean isRuled() {
        return ruled;
    }

    public String getRule() {
        return rule;
    }

    public boolean isHasArgument() {
        return hasArgument;
    }

    public int[] getArguments() {
        return arguments;
    }

    public void setFml(String fml) {
        this.fml = fml;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public void setArguments(int[] arguments) {
        this.arguments = arguments;
    }
}
