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
public class PTwoBox extends PNode {
    
    private PLine bso;
    private PLine bco;
    private PLine bst; 
    private PLine bct;
    private List<PNode> childreno;
    private List<PNode> childrent;
    public PTwoBox(PLine bso, PLine bco, PLine bst, PLine bct) {
        this.bso = bso; 
        this.bco = bco; 
        this.bst = bst; 
        this.bct = bct; 
        childreno = new ArrayList<PNode>();
        childrent = new ArrayList<PNode>();
        size = 4;
    }

    public void insertNodeo(int i, PNode p) {
        childreno.add(i, p);
    }

    public void deleteNode(PNode p) {
        if(childreno.contains(p)) { 
            childreno.remove(p);
        }
        if(childrent.contains(p)) { 
            childreno.remove(p);
        }
    }
    
    public void insertNodet(int i, PNode p) { 
        childrent.add(i, p);
    }

    public List<PNode> getChildreno() {
        return childreno;
    }

    public PNode getBso() {
        return bso;
    }

    public PNode getBco() {
        return bco;
    }

    public PNode getBst() {
        return bst;
    }

    public PNode getBct() {
        return bct;
    }
    
    public void incSize(int i) { 
        size+=i;
    }

    
}
