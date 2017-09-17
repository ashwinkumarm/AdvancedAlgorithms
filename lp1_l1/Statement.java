package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

public class Statement {

	String var;
	Num value = null;

	public Statement(String variable, String value) {
		var = variable;
		this.value = new Num(Long.parseLong(value));
	}
	
	public Statement(String variable, Num value) {
		var = variable;
		this.value = value;
	}
}
