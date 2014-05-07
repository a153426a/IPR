package ast;

public class IFFStatement extends BinaryOpStatement {

	public IFFStatement(LogicStatement nestedStatementLeft,
			LogicStatement nestedStatementRight) {
		super(Symbol.IFF, nestedStatementLeft, nestedStatementRight);
	}
        
        @Override
        public boolean equalsTo(LogicStatement l) {
            if(l instanceof IFFStatement) {
                if(((IFFStatement) l).nestedStatementLeft.equalsTo(nestedStatementLeft) && ((IFFStatement) l).nestedStatementRight.equalsTo(nestedStatementRight)) {
                    return true; 
                }
            }
            return false;
        }

}
