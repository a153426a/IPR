package ast;

public class Falsity extends Constant {

	public Falsity() {
		super(Symbol.FALSITY);
	}

	@Override
	public String toString() {
		return "(" + symbol.toString() + ")";
	}

    @Override
    public boolean equalsTo(LogicStatement l) {
        if(l instanceof Falsity) { 
            return true; 
        }
        return false; 
    }

}
