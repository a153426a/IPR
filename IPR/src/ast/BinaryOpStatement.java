package ast;

public abstract class BinaryOpStatement extends LogicStatement {

    public final LogicStatement nestedStatementLeft;
    public final LogicStatement nestedStatementRight;

    public BinaryOpStatement(Symbol symbol, LogicStatement nestedStatementLeft, LogicStatement nestedStatementRight) {
        super(symbol);
        this.nestedStatementLeft = nestedStatementLeft;
        this.nestedStatementRight = nestedStatementRight;
    }

    @Override
    public String toString() {
        if(!getBracket()) { 
            String left; 
            String right; 
            if(nestedStatementLeft.symbol.compareTo(symbol)<0) { 
                left = nestedStatementLeft.toString();
            } else { 
                left = "(" + nestedStatementLeft + ")";
            }

            if(nestedStatementRight.symbol.compareTo(symbol)<0) { 
                right = nestedStatementRight.toString();
            } else { 
                right = "(" + nestedStatementRight + ")";
            }

            return left+ symbol.toString() + right;
        } else { 
            return "(" +nestedStatementLeft + ")" + symbol.toString() + "(" + nestedStatementRight + ")";
        }
        
        
    }

}
