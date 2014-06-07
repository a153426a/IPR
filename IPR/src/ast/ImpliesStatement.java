package ast;

public class ImpliesStatement extends BinaryOpStatement {

    public ImpliesStatement(LogicStatement nestedStatementLeft,
            LogicStatement nestedStatementRight) {
        super(Symbol.IMPLIES, nestedStatementLeft, nestedStatementRight);
    }

    @Override
    public boolean equalsTo(LogicStatement l) {
        if (l instanceof ImpliesStatement) {
            if (((ImpliesStatement) l).nestedStatementLeft.equalsTo(nestedStatementLeft) && ((ImpliesStatement) l).nestedStatementRight.equalsTo(nestedStatementRight)) {
                return true;
            }
        }
        return false;
    }

}
