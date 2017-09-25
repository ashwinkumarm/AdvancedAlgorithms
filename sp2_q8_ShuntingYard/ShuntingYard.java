package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q8_ShuntingYard;

import java.util.HashMap;
import java.util.Stack;

/**
 * Class ShuntingYard
 * 
 * <P>
 * This class performs infix expression to postfix expression conversion
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class ShuntingYard {

	private final static HashMap<String, Operator> operatorMap = new HashMap<String, Operator>();

	/**
	 * Initializes the hashmap with operators, associativity and precedence
	 * 
	 */
	private static void initializeOperators() {

		operatorMap.put("+", new Operator("+", "left", 1));
		operatorMap.put("-", new Operator("-", "left", 1));
		operatorMap.put("*", new Operator("*", "left", 2));
		operatorMap.put("/", new Operator("/", "left", 2));
		operatorMap.put("^", new Operator("^", "right", 3));
		operatorMap.put("!", new Operator("!", "none", 4));
	}

	/**
	 * Performs the infix to postfix conversion
	 * 
	 * @param infix
	 * @return
	 */
	public static String infixToPostfix(String infix) {

		StringBuilder postfix = new StringBuilder();
		Stack<String> operatorStack = new Stack<String>();
		initializeOperators();
		for (String currentToken : infix.split("")) {
			// checks if its a operator
			if (operatorMap.containsKey(currentToken)) {
				while (!operatorStack.isEmpty()) {
					String topOperator = operatorStack.peek();
					if ((comparePrecedence(currentToken, topOperator) < 0)
							|| (isleftAssociative(currentToken) && comparePrecedence(currentToken, topOperator) == 0)) {
						postfix.append(operatorStack.pop());
					} else {
						break;
					}
				}
				operatorStack.push(currentToken);
			}

			else if (currentToken.equals("("))
				operatorStack.push(currentToken);

			else if (currentToken.equals(")")) {
				while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
					postfix.append(operatorStack.pop());
				}
				operatorStack.pop();
			}
			// pushes operand directly to output
			else {
				postfix.append(currentToken);
			}
		}
		// Pops from the stack when the stack is not empty at the end of input
		while (!operatorStack.isEmpty())
			postfix.append(operatorStack.pop());

		return postfix.toString();
	}

	/**
	 * Checks whether the given operator is left associative
	 * 
	 * @param op
	 * @return
	 */
	private static boolean isleftAssociative(String op) {

		return operatorMap.get(op).getAssociativity().equalsIgnoreCase("left");
	}

	/**
	 * Compares the precedence between the two given operators
	 * 
	 * @param currentTokenName
	 * @param topOperatorName
	 * @return
	 */
	private static int comparePrecedence(String currentTokenName, String topOperatorName) {

		if (operatorMap.get(topOperatorName) == null) {
			return 1;
		}
		return operatorMap.get(currentTokenName).getPrecedence() - operatorMap.get(topOperatorName).getPrecedence();

	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String args[]) {

		String infix = "5!+3";
		System.out.println("Postfix expression: " + infixToPostfix(infix));
	}

}
