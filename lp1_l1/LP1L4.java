// Starter code for Level 4 driver for lp1

// Change following line to your group number
package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Tokenizer;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Tokenizer.Token;

public class LP1L4 {
	
	// put in constant file. just adding here to check if its working 
	static int base;

	private static final String ADD = "+";
	private static final String SUBTRACT = "-";
	private static final String MULTIPLY = "*";
	private static final String DIVIDE = "/";
	private static final String MOD = "%";
	private static final String POWER = "^";
	private static final String SQUARE_ROOT = "|";
	private static final String SEMI_COLON = ";";
	private static final String EQUAL = "=";
	
	static ArrayList<InputString> inputArray = new ArrayList<InputString>();
	static Num[] variableMap = new Num[26];
	static HashMap<Integer, Integer> LineNoForLabel = new HashMap<>();

	// the jump can go backward or forward. Since forward is possible, we need to
	// read the entire inp first. Read the entire inp
	// store t n ob array. obj class will have varible to store the entire infix
	// expression. helper methods for converting and evaluating.
	// Have variable in the class to say if its a just a assign, expression or loop
	// conditions
	// Have hashmap for storing label and line number too.

	// Read the entire input again from first line. If t s not a loop condition,
	// then just update the values upto the specific line and
	// move to the next line. For updating we need to update for the specific object
	// plus we can have an num array like in lp3 and keep updating
	// so that referencing the value is easier.
	// if its a loop conditon, we can have an helper function which iterates from
	// specific label, retrieved from hashmap and it runs from the specific
	// line number to the line number of the current line. and returns the value of
	// loop variable. Its basically a loop where that value is greater than zero.

	
	public static Num postfixEvaluation(String expression) {

		Stack<Num> operandStack = new Stack<Num>();
		String token;
		int len = expression.length();
		for (int i = 0; i < len; i++) {
			token = String.valueOf(expression.charAt(i));
			if (!isOperator(token)) {
				if (!Character.isLetter(token.charAt(0))) { // *
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
					operandStack.push(Num.multiply(operand1, operand2));
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
	
	private static boolean isOperator(String token) {
		return token.equals(ADD) || token.equals(SUBTRACT) || token.equals(MULTIPLY) || token.equals(DIVIDE)
				|| token.equals(MOD) || token.equals(POWER) || token.equals(SQUARE_ROOT);
	}
	
	static void evaluateStatements(){
	for(int i =0; i < inputArray.size(); i++){
			InputString inputLine = inputArray.get(i);
			//Level 3 statement
			if(inputLine.getLabel() == -1){
				if(inputLine.isExpression){
					Num value = postfixEvaluation(inputLine.getPostfixExpression());
					int index = inputLine.getVariable().charAt(0) - 'a';
					variableMap[index] = value;
					inputLine.setValue(value);
					System.out.println(value);
				}else{
					if(inputLine.getVariable().length() > 0){
					System.out.println(variableMap[inputLine.getVariable().charAt(0) - 'a']);
					}else{
						//last line
						Num r = variableMap[inputArray.get(i-1).getVariable().charAt(0)-'a'];
						r.printList();
					}
				}
			}else{
				//Level 4 statements
				if(inputLine.isLoop){
					int index = inputLine.getVariable().charAt(0) - 'a';
					if(variableMap[index].isZero()){
						if(inputLine.getZr() != -1){
							i = LineNoForLabel.get(inputLine.getZr()) -1;	
						}/*else{
							i = i;
						}*/
						continue;
					}else{
						i = LineNoForLabel.get(inputLine.getNz()) - 1;
						continue;
					}
				}else{
					Num value = postfixEvaluation(inputLine.getPostfixExpression());
					int index = inputLine.getVariable().charAt(0) - 'a';
					variableMap[index] = value;
					inputLine.setValue(value);
				}
			}
		}
	}
	static void formInputArray(Scanner in) throws Exception {
		int i = 0;
		InputString inp = new InputString();
		Token token;
		whileloop:
		while (in.hasNext()) {
			String word = in.next();
			token = Tokenizer.tokenize(word);
			switch(token)
			{
			case EOL:
				i++;
				inputArray.add(inp);
				if(inp.getVariable().length() == 0){
					break whileloop;	
				}
				inp = new InputString();
				break;
			case NUM:
				LineNoForLabel.put(Integer.parseInt(word), i);
				inp.setLabel(Integer.parseInt(word));
				break;
			case VAR:
				inp.setVariable(word);
				break;
			case EQ:
				i++;
				readRightHandSide(in, inp);
				inp = new InputString();
				break;
			case QM:
				i++;
				inp.setLoop(true);
				readLoopCondition(in, inp);
				inp = new InputString();
				break;
			default:
				System.out.println("Enter valid expression");
			}
			}
	}
	
	public static void readRightHandSide(Scanner in, InputString inp) throws Exception {
		StringBuilder exp = new StringBuilder();
		while (in.hasNext()) {
			String word = in.next();
			Token token = Tokenizer.tokenize(word);
			if (!token.equals(Token.EOL)) {
				exp.append(word);
			} else {
				if (exp.toString().matches("\\d+")) {
					inp.setValue(new Num(exp.toString()));
					int index = inp.getVariable().charAt(0) - 'a';
					variableMap[index] = new Num(exp.toString());
					inp.setAssgn(true);
				} else {
					inp.setPostfixExpression(ShuntingYard.infixToPostfix(exp.toString()));
					inp.setExpression(true);
				}
				inputArray.add(inp);
				
				break;
			}
		}
	}
	
	public static void readLoopCondition(Scanner in, InputString inp) throws Exception {
		StringBuilder exp = new StringBuilder();
		while (in.hasNext()) {
			String word = in.next();
			Token token = Tokenizer.tokenize(word);
			if (!token.equals(Token.EOL)) {
				exp.append(word);
			} else {
				if (exp.toString().matches("\\d+")) {
					inp.setNz(Integer.parseInt(exp.toString()));
				} else {
					String[] labels = exp.toString().split(":");
					inp.setNz(Integer.parseInt(labels[0]));
					inp.setZr(Integer.parseInt(labels[1]));
				}
				inputArray.add(inp);
				break;
			}
		}

	}

	public static void main(String[] args) throws Exception {
		Scanner in;
		if (args.length > 0) {
			 base = Integer.parseInt(args[0]);
			// Use above base for all numbers (except I/O, which is in base 10)
		}

		in = new Scanner(System.in);
		formInputArray(in);
		evaluateStatements();
	}
}
