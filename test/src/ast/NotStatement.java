package ast;

public class NotStatement extends UnaryOpStatement {
	
	public NotStatement(LogicStatement nestedStatement) {
		super(Symbol.NOT, nestedStatement);
	}
	
	@Override
	public String toString() {
		return "(" + symbol.toString() + nestedStatement + ")";
	}

}
