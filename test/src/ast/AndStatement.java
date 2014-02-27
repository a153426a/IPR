package ast;

public class AndStatement extends BinaryOpStatement {

	public AndStatement(LogicStatement nestedStatementLeft,
			LogicStatement nestedStatementRight) {
		super(Symbol.AND, nestedStatementLeft, nestedStatementRight);
	}

}
