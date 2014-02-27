package ast;

public enum Symbol {
	VARIABLE(),
	TRUTH('⊤'),
	FALSITY('⊥'),
	FORALL('∀'),
	NOT('¬'),
	AND('∧'),
	OR('∨'),
	IMPLIES('→'),
	IFF('↔'),
	THEREEXISTS('∃'),
	EQUALS('='),
	PARAMETERS(),
	PREDICATE('≡');
	
	public final char symbol;
	
	private Symbol() {
		symbol = 0;
	}
	
	private Symbol(char c) {
		symbol = c;
	}
	
	@Override
	public String toString() {
		if(symbol == 0) {
			throw new RuntimeException("The type does not have corrosponding char value");
		}
		return String.valueOf(symbol);
	}
	
}
