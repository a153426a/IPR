package ast;

public class Truth extends Constant {

    public Truth() {
        super(Symbol.TRUTH);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }

    @Override
    public boolean equalsTo(LogicStatement l) {
        if (l instanceof Truth) {
            return true;
        }
        return false;
    }
    
}
