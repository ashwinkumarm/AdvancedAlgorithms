package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

public class InputString {
	
	String variable;
	String label ;
	int lineNo = -1;
	String expression = "";
	Num value ;
	
	InputString(){
	}
	
	InputString(InputString exp){
		this.variable = exp.variable;
		this.label = exp.label;
		this.lineNo = exp.lineNo;
		this.expression = exp.expression;
		this.value = exp.value;
	}
	
	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Num getValue() {
		return value;
	}

	public void setValue(Num value) {
		this.value = value;
	}

	
	

}
