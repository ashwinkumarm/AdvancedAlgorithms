package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q8_ShuntingYard;

import java.util.HashMap;
import java.util.Stack;

public class ShuntingYard1 {

	enum Operator {
		ADD(1, 1), SUBTRACT(1, 1), MULTIPLY(2, 1), DIVIDE(2, 1), MODULUS(2, 1), EXPONENT(3, 2), FACTORIAL(4, 0);

		final int precedence, associativity;

		private Operator(int precedence, int associativity) {
			this.precedence = precedence;
			this.associativity = associativity;
		}
	}

	private static HashMap<String, Operator> operatorMap = new HashMap<String, Operator>();

	public static String infixToPostfix(String infix) {
		StringBuilder postfix = new StringBuilder();
		Stack<String> st = new Stack<String>();

		initializeOperators();
		for (String ch : infix.split("")) {
			if (operatorMap.containsKey(ch)) {
				while (!st.isEmpty()) {
					String topOperator = st.peek();
					if ((operatorMap.containsKey(topOperator) && isleftAssociative(ch)
							&& comparePrecedence(ch, topOperator) <= 0)
							|| (operatorMap.containsKey(topOperator) && !isleftAssociative(ch)
									&& comparePrecedence(ch, topOperator) < 0))
						postfix.append(st.pop());
					else
						break;
				}
				st.push(ch);
			}

			else if (ch.equals("("))
				st.push(ch);

			else if (ch.equals(")")) {
				while (!st.isEmpty() && !st.peek().equals("(")) {
					postfix.append(st.pop());
				}
				st.pop();
			}

			else
				postfix.append(ch);
		}
		while (!st.isEmpty())
			postfix.append(st.pop());

		return postfix.toString();
	}

	private static boolean isleftAssociative(String ch) {
		return operatorMap.get(ch).associativity == 1;
	}

	private static int comparePrecedence(String op1, String op2) {
		int op1Precedence = operatorMap.get(op1).precedence;
		int op2Precedence = operatorMap.get(op2).precedence;
		if (op1Precedence == op2Precedence)
			return 0;
		else if (op1Precedence < op2Precedence)
			return -1;
		else
			return 1;
	}

	private static void initializeOperators() {
		operatorMap.put("+", Operator.ADD);
		operatorMap.put("-", Operator.SUBTRACT);
		operatorMap.put("*", Operator.MULTIPLY);
		operatorMap.put("/", Operator.DIVIDE);
		operatorMap.put("%", Operator.MODULUS);
		operatorMap.put("^", Operator.EXPONENT);
		operatorMap.put("!", Operator.FACTORIAL);

	}

	public static void main(String args[]) {
		String infix = "( A + B ) * C - ( D - E ) * ( F + G )";
		System.out.println("Postfix expression: " + infixToPostfix(infix));
	}

}
