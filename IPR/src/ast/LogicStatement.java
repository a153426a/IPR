package ast;

import java.util.ArrayList;
import java.util.List;

public abstract class LogicStatement {

    public final Symbol symbol;
    public boolean bracket;

    public LogicStatement(Symbol symbol) {
        this.symbol = symbol;
        bracket = false; 
    }

    @Override
    public abstract String toString();

    public abstract boolean equalsTo(LogicStatement l);
    
    //public abstract String noBracket();
    
    public boolean getBracket() { 
        return bracket; 
    }
    
    public void setBracket(boolean b) { 
        bracket = b;
    }

}
