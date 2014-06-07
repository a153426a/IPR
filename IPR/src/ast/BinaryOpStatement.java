package ast;

public abstract class BinaryOpStatement extends LogicStatement {

    public final LogicStatement nestedStatementLeft;
    public final LogicStatement nestedStatementRight;

    public BinaryOpStatement(Symbol symbol, LogicStatement nestedStatementLeft, LogicStatement nestedStatementRight) {
        super(symbol);
        this.nestedStatementLeft = nestedStatementLeft;
        this.nestedStatementRight = nestedStatementRight;
    }

    @Override
    public String toString() {
        return "(" + nestedStatementLeft + symbol.toString() + nestedStatementRight + ")";
    }

}
