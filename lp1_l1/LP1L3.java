// Starter code for Level 3 driver for lp1

// Change following line to your group number
package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

import java.util.Scanner;
import java.util.Stack;

/**
 * This class performs postfix expression evaluation
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class LP1L3 {
	//List of constants
	private static final String ADD = "+";
	private static final String SUBTRACT = "-";
	private static final String MULTIPLY = "*";
	private static final String DIVIDE = "/";
	private static final String MOD = "%";
	private static final String POWER = "^";
	private static final String SQUARE_ROOT = "|";
	private static final String SEMI_COLON = ";";
	private static final String EQUAL = "=";
	
	//Num array to store the variables [a-z]
	private static Num[] variableMap = new Num[26];

	/**
	 * Performs the evaluation of postfix expression using stack
	 * @param expression
	 * @return
	 */
	public static Num postfixEvaluation(String expression) {

		Stack<Num> operandStack = new Stack<Num>();
		String token;
		int len = expression.split(" ").length;
		String[] expArray = expression.split(" ");
		for (int i = 0; i < len; i++) {
			token = expArray[i];
			if (!isOperator(token)) {
				if (!Character.isLetter(token.charAt(0))) {
					operandStack.push(new Num(token));
				} else {
					int index = token.charAt(0) - 'a';
					operandStack.push(variableMap[index]);
				}
			} else {
				Num operand1 = operandStack.pop();
				Num operand2 = null;

				if (!token.equals(SQUARE_ROOT)) {
					operand2 = operandStack.pop();
				}
				switch (token) {
				case ADD:
					operandStack.push(Num.add(operand1, operand2));
					break;

				case SUBTRACT:
					operandStack.push(Num.subtract(operand2, operand1));
					break;

				case MULTIPLY:
					operandStack.push(Num.product(operand1, operand2));
					break;

				case DIVIDE:
					operandStack.push(Num.divide(operand2, operand1));
					break;

				case MOD:
					operandStack.push(Num.mod(operand2, operand1));
					break;

				case POWER:
					operandStack.push(Num.power(operand2, operand1));
					break;

				case SQUARE_ROOT:
					operandStack.push(Num.squareRoot(operand1));
					break;
				default:
					throw new IllegalStateException("Invalid Operator");
				}
			}
		}

		return operandStack.pop();
	}

	/**
	 * Return whether the given operator is in the following list of operators
	 * Add,subtract, multiply,divide,mod,exponent,square root
	 * 
	 * @param token
	 * @return
	 */
	private static boolean isOperator(String token) {
		return token.equals(ADD) || token.equals(SUBTRACT) || token.equals(MULTIPLY) || token.equals(DIVIDE)
				|| token.equals(MOD) || token.equals(POWER) || token.equals(SQUARE_ROOT);
	}

	/**
	 * Reads the given expression,parses the same and performs the postfix evaluation
	 * @param in
	 */
	private static void readExpression(Scanner in) {
		int index = -1;
		String variable = null;
		StringBuilder expression = new StringBuilder();

		while (in.hasNext()) {
			String word = in.next();

			if (word.equals(SEMI_COLON))
				break;

			else if (word.equals(EQUAL)) {
				String word1;
				while (in.hasNext()) {
					word1 = in.next();
					if (!word1.equals(SEMI_COLON)){
						expression.append(word1);
						expression.append(" ");
					}
					else {
						index = variable.charAt(0) - 'a';
						if (expression.length() == 1) {
							variableMap[index] = new Num(expression.toString());
							System.out.print("\n"+variableMap[index]);
						} else {
							variableMap[index] = postfixEvaluation(expression.toString());
							System.out.print("\n"+variableMap[index]);
						}
						break;
					}
				}
				expression = new StringBuilder();
			} else {
				variable = word;
			}
		}
		variableMap[index].printList();
		in.close();
	}

	/**
	 * Main method for testing
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		// LP1L3 x = new LP1L3();

		readExpression(in);

	}
}
