package ast;

public class Truth extends Constant {

	public Truth() {
		super(Symbol.TRUTH);
	}

	@Override
	public String toString() {
		return "(" + symbol.toString() + ")";
	}

}
