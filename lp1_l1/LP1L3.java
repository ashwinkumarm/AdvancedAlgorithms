// Starter code for Level 3 driver for lp1

// Change following line to your group number
package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1.Num;

public class LP1L3 {

	private static HashMap<String, Statement> variableMap = new HashMap<>();

	private static Num postfixEvaluation(ArrayList<String> expression) {

		Stack<Num> operandStack = new Stack<Num>();
		String token;
		int len = expression.size();
		
		for(int i= 0;i<len;i++) {
			token = expression.get(i);
			if (!isOperator(token)) {
				if(!Character.isLetter(token.charAt(0))) {
					operandStack.push(variableMap.get(token).value);
				}
				else {
					operandStack.push(new Num(token));
				}
			}
			else {
				Num operand1 = null;
				Num operand2 = null;
				if(!token.equals("|")){
				operand1 = operandStack.pop();
				operand2 = operandStack.pop();
				}
				switch(token) {
				case "+":
					operandStack.push(Num.add(operand1, operand2));
					break;
					
				case "-":
					operandStack.push(Num.subtract(operand1, operand2));
					break;
				
				case "*":
					operandStack.push(Num.multiply(operand1, operand2));
					break;
				
				case "/":
					operandStack.push(Num.divide(operand1, operand2));
					break;
				
				case "%":
					operandStack.push(Num.mod(operand1, operand2));
					break;
				
				case "^":
					operandStack.push(Num.power(operand1, operand2));
					break;
					
				case "|":
					operandStack.push(Num.squareRoot(operandStack.pop()));
					break;
				}
			}
		}

		return operandStack.pop();
	}

	private static boolean isOperator(String token) {
		return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%")
				|| token.equals("^") || token.equals("|");
	}

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		//LP1L3 x = new LP1L3();

		// a = new Num(999);
		// b = new Num(8);
		// Num c = postfixEvaluation("a b ^");
		// Num d = postfixEvaluation("a b +");
		String variable = null;
		ArrayList<String> expression = new ArrayList<>();

		while (in.hasNext()) {
			String word = in.next();

			if (word.equals(";"))
				break;

			else if (word.equals("=")) {
				String word1;
				while (in.hasNext()) {
					word1 = in.next();
					if (!word1.equals(";"))
						expression.add(in.next());
					else {
						if (expression.size() == 1)
							variableMap.put(variable, new Statement(variable, expression));
						else
							variableMap.put(variable, new Statement(variable, postfixEvaluation(expression)));
					}
				}
				expression = new ArrayList<>();
			}

			else {
				variable = word;
			}
		}

	}
}
