package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

/**
 * 
 * @author deepaks
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

	public int getNz() {
		return nz;
	}

	public void setNz(int nz) {
		this.nz = nz;
	}

	public int getZr() {
		return zr;
	}

	public void setZr(int zr) {
		this.zr = zr;
	}

	InputString() {
	}

	InputString(InputString exp) {
		this.variable = exp.variable;
		this.label = exp.label;
		this.isAssgn = exp.isAssgn;
		this.isLoop = exp.isLoop;
		this.isExpression = exp.isExpression;
		this.value = exp.value;
	}


	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}


	public String getPostfixExpression() {
		return postfixExpression;
	}

	public void setPostfixExpression(String postfixExpression) {
		this.postfixExpression = postfixExpression;
	}

	public boolean isAssgn() {
		return isAssgn;
	}

	public void setAssgn(boolean isAssgn) {
		this.isAssgn = isAssgn;
	}

	public boolean isLoop() {
		return isLoop;
	}

	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}

	public boolean isExpression() {
		return isExpression;
	}

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
