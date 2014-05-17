/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

/**
 *
 * @author zl2511
 */
public class TwoBoxClosingLine extends boxClosingLine {

    public TwoBoxClosingLine(int number) {
        super(number);
    }
    
    public void setStartLine(TwoBoxStartingLine tbs) { 
        super.setStartLine(tbs);
    }
    
    @Override 
    public TwoBoxStartingLine getStartLine() { 
        return (TwoBoxStartingLine) super.getStartLine();
    }
    
}
