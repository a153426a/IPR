package ast;

public class OrStatement extends BinaryOpStatement {

	public OrStatement(LogicStatement nestedStatementLeft,
			LogicStatement nestedStatementRight) {
		super(Symbol.OR, nestedStatementLeft, nestedStatementRight);
	}

}