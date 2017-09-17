package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

import java.util.ArrayList;

public class Statement {

	String var;
	Num value = null;

	public Statement(String variable, ArrayList<String> expression) {
		var = variable;
		value = new Num(expression.get(0));
	}
	
	public Statement(String variable, Num value) {
		var = variable;
		this.value = value;
	}
}
