package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q8_ShuntingYard;

import java.util.HashMap;
import java.util.Stack;


public class ShuntingYard {

	private final static HashMap<String, Operator> operatorMap = new HashMap<String, Operator>();

	private static void initializeOperators() {
		operatorMap.put("+", new Operator("+","left",1));
		operatorMap.put("-", new Operator("-","left",1));
		operatorMap.put("*", new Operator("*","left",2));
		operatorMap.put("/", new Operator("/","left",2));
		operatorMap.put("^", new Operator("^","right",3));
		operatorMap.put("!", new Operator("!","none",4));
	}

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
							|| (isleftAssociative(currentToken) && comparePrecedence(currentToken, topOperator) == 0)){
						postfix.append(operatorStack.pop());
					}else{
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
			else{
				postfix.append(currentToken);
			}
		}
		while (!operatorStack.isEmpty())
			postfix.append(operatorStack.pop());

		return postfix.toString();
	}

	private static boolean isleftAssociative(String op) {
		return operatorMap.get(op).getAssociativity().equalsIgnoreCase("left");
	}

	private static int comparePrecedence(String currentTokenName, String topOperatorName) {
		if(operatorMap.get(topOperatorName) == null){
			return 1;
		}
		return operatorMap.get(currentTokenName).getPrecedence() - operatorMap.get(topOperatorName).getPrecedence();

	}

	public static void main(String args[]) {
		String infix = "(a+b)*(c-d)";
		System.out.println("Postfix expression: " + infixToPostfix(infix));
	}

}
