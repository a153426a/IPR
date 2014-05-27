/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zl2511
 */
public class boxStartingLine extends ProveLine {
    
    private List<ProveLine> LineInBox; 
    private List<boxStartingLine> subBoxes; 
    private boxClosingLine endLine;
    private List<boxStartingLine> parentBoxes;

    public boxStartingLine(int number) {
        super(number);
        LineInBox = new ArrayList<ProveLine>(); 
        LineInBox.add(this);
        subBoxes = new ArrayList<boxStartingLine>();
        parentBoxes = new ArrayList<boxStartingLine>();
        
    }
    
    public List<ProveLine> getLineInBox() { 
        return LineInBox;
    }
    
    public List<boxStartingLine> getSubBoxes() { 
        return subBoxes; 
    }
    
    public void addLineInBox(ProveLine pl) { 
        LineInBox.add(pl);
    }
    
    public void deleteLineInBox(ProveLine pl) { 
        if(LineInBox.contains(pl)) { 
            
            LineInBox.remove(pl);
        }
    }
    
    public void addSubBoxes(boxStartingLine b) { 
        subBoxes.add(b);
    } 
    
    public void deleteSubBoxed(boxStartingLine b) { 
        if(subBoxes.contains(b)) { 
            
            subBoxes.remove(b);
        }
    }
    
    public boxClosingLine getEndLine() { 
        return endLine;
    }
    
    public void setEndLine(boxClosingLine bs) { 
        endLine = bs;
        LineInBox.add(bs);
    }
    
    public List<boxStartingLine> getParentBox() { 
        return parentBoxes;
    }
    
    public void addParentBox(boxStartingLine bs) { 
        parentBoxes.add(bs);
    }
    
}
