package ast;

public class NotStatement extends UnaryOpStatement {

    public NotStatement(LogicStatement nestedStatement) {
        super(Symbol.NOT, nestedStatement);
    }

    @Override
    public String toString() {
        if(!getBracket()) { 
            String right;
            if(nestedStatement.symbol.compareTo(symbol)<0) { 
                right = nestedStatement.toString();
            } else { 
                right = "(" + nestedStatement + ")";
            }
            return symbol.toString() + right;
        } else { 
            return symbol.toString() + "(" + nestedStatement + ")";
        }
        
        
        
    }

    @Override
    public boolean equalsTo(LogicStatement l) {
        if (l instanceof NotStatement) {
            if (((NotStatement) l).nestedStatement.equalsTo(nestedStatement)) {
                return true;
            }
        }
        return false;
    }
    
}
