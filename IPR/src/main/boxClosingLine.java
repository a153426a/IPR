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
public class boxClosingLine extends ProveLine {
    
    private boxStartingLine startLine; 

    public boxClosingLine(int number) {
        super(number);
        
    }
    
    public boxStartingLine getStartLine() { 
        return startLine;
    }
    
    public void setStartLine(boxStartingLine bs) { 
        startLine = bs;
    }
    
}
