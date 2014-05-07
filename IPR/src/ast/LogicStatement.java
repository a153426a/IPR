package ast;

import java.util.ArrayList;
import java.util.List;

public abstract class LogicStatement {
	
	public final Symbol symbol;
	public LogicStatement(Symbol symbol) {
		this.symbol = symbol;
	}
		
	@Override
	public abstract String toString();
        public abstract boolean equalsTo(LogicStatement l);

}
