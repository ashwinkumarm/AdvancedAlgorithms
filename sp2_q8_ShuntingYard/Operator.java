package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q8_ShuntingYard;

public class Operator {
	private String name;
	private String associativity;
	private int precedence;

	Operator(String name, String associativity, int precedence) {
		this.name = name;
		this.associativity = associativity;
		this.precedence = precedence;
	}

	public String getName() {
		return name;
	}

	public String getAssociativity() {
		return associativity;
	}

	public int getPrecedence() {
		return precedence;
	}

}
