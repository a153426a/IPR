package ast;

public class Variable extends LogicStatement {
	
	public final String name;
	
	public Variable(String name) {
		super(Symbol.VARIABLE);
		this.name = name;
	}

	@Override
	public String toString() {
		return "(" + name + ")";
	}

    @Override
    public boolean equalsTo(LogicStatement l) {
        if(l instanceof Variable) { 
            if (((Variable) l).name.equals(name)) { 
                return true;
            }
        }
        return false; 
    }
        
        

    

}
