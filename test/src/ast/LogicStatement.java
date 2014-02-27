package ast;

public abstract class LogicStatement {
	
	public final Symbol symbol;
	
	public LogicStatement(Symbol symbol) {
		this.symbol = symbol;
	}
		
	@Override
	public abstract String toString();

}
