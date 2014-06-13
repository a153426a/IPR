/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.SaveP;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zl2511
 */
public class PBox extends PNode {

    private PLine bs; 
    private PLine bc; 
    private List<PNode> children;

    public PBox(PLine bs, PLine bc) {
        this.bs = bs; 
        this.bc = bc; 
        children = new ArrayList<PNode>();
        size = 2;
    }

    public void insertNode(int i, PNode p) {
        children.add(i, p);
    }

    public void deleteNode(PNode p) {
        if(children.contains(p)) { 
            children.remove(p);
        }
    }

    public List<PNode> getChildren() {
        return children;
    }

    public PNode getBs() {
        return bs;
    }

    public PNode getBc() {
        return bc;
    }
    
    public void incSize(int i) { 
        size+=i;
    }
    
}
