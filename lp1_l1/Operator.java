package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

/**
 * This class stores the operator symbol, associativity, precedence
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class Operator {

	private String name;
	private String associativity;
	private int precedence;

	/**
	 * Constructor to initialize the name, associativity and precedence
	 * 
	 * @param name
	 * @param associativity
	 * @param precedence
	 */
	Operator(String name, String associativity, int precedence) {

		this.name = name;
		this.associativity = associativity;
		this.precedence = precedence;
	}

	/**
	 * Getter method for name which returns the name(symbol)
	 * 
	 * @return
	 */
	public String getName() {

		return name;
	}

	/**
	 * Getter method for associativity which returns the associativity
	 * 
	 * @return
	 */
	public String getAssociativity() {

		return associativity;
	}

	/**
	 * Getter method for precedence which returns the precedence
	 * 
	 * @return
	 */
	public int getPrecedence() {

		return precedence;
	}

}
