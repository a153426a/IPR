package ast;

public class AndStatement extends BinaryOpStatement {

	public AndStatement(LogicStatement nestedStatementLeft,
			LogicStatement nestedStatementRight) {
		super(Symbol.AND, nestedStatementLeft, nestedStatementRight);
	}

    @Override
    public boolean equalsTo(LogicStatement l) {
        if(l instanceof AndStatement) {
            System.out.println(nestedStatementLeft.getClass());
            System.out.println(nestedStatementRight.getClass());
            System.out.println(((AndStatement) l).nestedStatementLeft.getClass());
            System.out.println(((AndStatement) l).nestedStatementRight.getClass());
            if(((AndStatement) l).nestedStatementLeft.equalsTo(nestedStatementLeft) && ((AndStatement) l).nestedStatementRight.equalsTo(nestedStatementRight) 
                    || ((AndStatement) l).nestedStatementLeft.equalsTo(nestedStatementRight) && ((AndStatement) l).nestedStatementRight.equalsTo(nestedStatementLeft)) {
                return true; 
            }
        }
        return false;
    }

}
