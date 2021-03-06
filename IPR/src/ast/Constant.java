package ast;

import java.util.ArrayList;

public abstract class Constant extends LogicStatement {

    public Constant(Symbol symbol) {
        super(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }

}
