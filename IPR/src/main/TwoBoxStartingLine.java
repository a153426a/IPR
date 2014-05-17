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
public class TwoBoxStartingLine extends boxStartingLine {
    
    private TwoBoxStartingLine pair; 
    private boolean first; 

    public TwoBoxStartingLine(int number) {
        super(number);
        first = false; 
    }
    
    public TwoBoxStartingLine getPair() { 
        return pair; 
    }
    
    public void setPair(TwoBoxStartingLine tbs) { 
        pair = tbs;
    }
    
    public boolean getFirst() { 
        return first; 
    }
    
    public void setFirst() { 
        first = true;
    }
    
    public void setEndLine(TwoBoxClosingLine tbs) { 
        super.setEndLine(tbs);
    }
    
    @Override 
    public TwoBoxClosingLine getEndLine() { 
        return (TwoBoxClosingLine) super.getEndLine();
    }
    
}
