package ast;

public class IFFStatement extends BinaryOpStatement {

	public IFFStatement(LogicStatement nestedStatementLeft,
			LogicStatement nestedStatementRight) {
		super(Symbol.IFF, nestedStatementLeft, nestedStatementRight);
	}

}
