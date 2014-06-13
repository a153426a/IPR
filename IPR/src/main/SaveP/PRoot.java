/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.SaveP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zl2511
 */
public class PRoot implements Serializable {
    private List<PNode> children; 
    private int size; 
    public PRoot() { 
        children = new ArrayList<PNode>();
        size = 0; 
    }
    
    public void insertNode(int i, PNode p) { 
        
    }
    
    public void deleteNode(PNode p) { 
        if(children.contains(p)) { 
            children.remove(p);
        }
    }
    
    public List<PNode> getChildren() { 
        return children;
    }

    public int getSize() {
        return size;
    }
    
    public void incSize(int i) { 
        size+=i;
    }
    
}
