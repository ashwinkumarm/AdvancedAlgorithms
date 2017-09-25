package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

/**
 * This class stores the variables needed for the given input statements
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class InputString {
	String variable = "";
	int label = -1;
	String postfixExpression = "";
	Num value;
	boolean isAssgn;
	boolean isLoop;
	boolean isExpression;
	int nz = -1;
	int zr = -1;

	/**
	 * Getter method for Nz
	 * 
	 * @return
	 */
	public int getNz() {
		return nz;
	}

	/**
	 * Setter method for Nz
	 * 
	 * @return
	 */
	public void setNz(int nz) {
		this.nz = nz;
	}

	/**
	 * Getter method for Zr
	 * 
	 * @return
	 */
	public int getZr() {
		return zr;
	}

	/**
	 * Setter method for Zr
	 * 
	 * @return
	 */
	public void setZr(int zr) {
		this.zr = zr;
	}

	InputString() {
	}

	/**
	 * Constructor to store the variables
	 * 
	 * @param exp
	 */
	InputString(InputString exp) {
		this.variable = exp.variable;
		this.label = exp.label;
		this.isAssgn = exp.isAssgn;
		this.isLoop = exp.isLoop;
		this.isExpression = exp.isExpression;
		this.value = exp.value;
	}

	/**
	 * Getter method for variables
	 * 
	 * @return
	 */
	public String getVariable() {
		return variable;
	}

	/**
	 * Setter method for variables
	 * 
	 * @return
	 */
	public void setVariable(String variable) {
		this.variable = variable;
	}

	/**
	 * Getter method for labels
	 * 
	 * @return
	 */
	public int getLabel() {
		return label;
	}

	/**
	 * Setter method for labels
	 * 
	 * @return
	 */
	public void setLabel(int label) {
		this.label = label;
	}

	/**
	 * Getter method for postfix expression
	 * 
	 * @return
	 */
	public String getPostfixExpression() {
		return postfixExpression;
	}

	/**
	 * Setter method for postfix expression
	 * 
	 * @return
	 */
	public void setPostfixExpression(String postfixExpression) {
		this.postfixExpression = postfixExpression;
	}

	public boolean isAssgn() {
		return isAssgn;
	}

	/**
	 * Setter method for assignment operation
	 * 
	 * @return
	 */
	public void setAssgn(boolean isAssgn) {
		this.isAssgn = isAssgn;
	}

	public boolean isLoop() {
		return isLoop;
	}

	/**
	 * Setter method for loop operation
	 * 
	 * @return
	 */
	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}

	public boolean isExpression() {
		return isExpression;
	}

	/**
	 * Setter method to set boolean for isExpression as an expression or not
	 * 
	 * @return
	 */
	public void setExpression(boolean isExpression) {
		this.isExpression = isExpression;
	}

	public Num getValue() {
		return value;
	}

	public void setValue(Num value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return variable + " = " + postfixExpression;
	}

}
