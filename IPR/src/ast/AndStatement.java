package ast;

public class AndStatement extends BinaryOpStatement {

    public AndStatement(LogicStatement nestedStatementLeft,
            LogicStatement nestedStatementRight) {
        super(Symbol.AND, nestedStatementLeft, nestedStatementRight);
    }

    @Override
    public boolean equalsTo(LogicStatement l) {
        if (l instanceof AndStatement) {
            if (((AndStatement) l).nestedStatementLeft.equalsTo(nestedStatementLeft) && ((AndStatement) l).nestedStatementRight.equalsTo(nestedStatementRight)
                    || ((AndStatement) l).nestedStatementLeft.equalsTo(nestedStatementRight) && ((AndStatement) l).nestedStatementRight.equalsTo(nestedStatementLeft)) {
                return true;
            }
        }
        return false;
    }

}
