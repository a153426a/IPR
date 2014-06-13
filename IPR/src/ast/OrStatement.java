package ast;

public class OrStatement extends BinaryOpStatement {

    public OrStatement(LogicStatement nestedStatementLeft,
            LogicStatement nestedStatementRight) {
        super(Symbol.OR, nestedStatementLeft, nestedStatementRight);
    }

    @Override
    public boolean equalsTo(LogicStatement l) {
        if (l instanceof OrStatement) {
            if (((OrStatement) l).nestedStatementLeft.equalsTo(nestedStatementLeft) && ((OrStatement) l).nestedStatementRight.equalsTo(nestedStatementRight)
                    || ((OrStatement) l).nestedStatementLeft.equalsTo(nestedStatementRight) && ((OrStatement) l).nestedStatementRight.equalsTo(nestedStatementLeft)) {
                return true;
            }
        }
        return false;
    }
    
    

}
