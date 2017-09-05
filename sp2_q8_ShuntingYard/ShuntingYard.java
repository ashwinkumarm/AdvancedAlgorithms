package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q8_ShuntingYard;

import java.util.HashMap;
import java.util.Stack;

public class ShuntingYard {

	final static HashMap<String, int[]> operatorMap = new HashMap<String, int[]>();

	static class Operator {
		String symbol;

		public Operator(String symbol, int associativity, int precedence) {
			this.symbol = symbol;
			int[] tmp = { associativity, precedence };
			operatorMap.put(this.symbol, tmp);
		}
	}

	public static void initializeOperators() {
		new Operator("+", 1, 1);
		new Operator("-", 1, 1);
		new Operator("*", 1, 2);
		new Operator("/", 1, 2);
		new Operator("%", 1, 2);
		new Operator("^", 2, 3);
		new Operator("!", 0, 4);
	}

	public static String infixToPostfix(String infix) {
		StringBuilder postfix = new StringBuilder();
		Stack<String> st = new Stack<String>();

		initializeOperators();
		for (String ch : infix.split("")) {
			if (operatorMap.containsKey(ch)) {
				while (!st.isEmpty()) {
					String topOperator = st.peek();
					if ((!topOperator.equals("(") && isleftAssociative(ch) && comparePrecedence(ch, topOperator)<=0)
							|| (!topOperator.equals("(") && !isleftAssociative(ch) && comparePrecedence(ch, topOperator)<0))
						postfix.append(st.pop());
					else
						break;
				}
				st.push(ch);
			}

			else if (ch .equals("("))
				st.push(ch);

			else if (ch.equals(")")) {
				while (!st.isEmpty() && !st.peek().equals("(")) {
					postfix.append(st.pop());
				}
				st.pop();
			} else
				postfix.append(ch);
		}
		while (!st.isEmpty())
			postfix.append(st.pop());

		return postfix.toString();
	}

	public static boolean isleftAssociative(String op) {
		return operatorMap.get(op)[0] == 1;
	}

	public static int comparePrecedence(String op1, String op2) {
		int op1Precedence = operatorMap.get(op1)[1];
		int op2Precedence = operatorMap.get(op2)[1];
		if(op1Precedence < op2Precedence)
			return -1;
		else if(op1Precedence > op2Precedence)
			return 1;
		else
			return 0;
	}

	public static void main(String args[]) {
		String infix = "3+4*2/(1-5)^2^3";
		System.out.println("Postfix expression: " + infixToPostfix(infix));
	}

}
