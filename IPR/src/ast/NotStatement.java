package ast;

public class NotStatement extends UnaryOpStatement {

    public NotStatement(LogicStatement nestedStatement) {
        super(Symbol.NOT, nestedStatement);
    }

    @Override
    public String toString() {
        return "(" + symbol.toString() + nestedStatement + ")";
    }

    @Override
    public boolean equalsTo(LogicStatement l) {
        if (l instanceof NotStatement) {
            if (((NotStatement) l).nestedStatement.equalsTo(nestedStatement)) {
                return true;
            }
        }
        return false;
    }

}
