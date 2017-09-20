// Starter code for Level 4 driver for lp1

// Change following line to your group number
package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Tokenizer;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Tokenizer.Token;

public class LP1L4 {
	static ArrayList<InputString> inputArray = new ArrayList<InputString>();
	static Num[] variableMap = new Num[26];
	static HashMap<String, Integer> LineNoForLabel = new HashMap<>();

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

	static void computeInputArray(Scanner in) throws Exception {
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
				LineNoForLabel.put(word, i);
				inp.setLabel(word);
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
			int base = Integer.parseInt(args[0]);
			// Use above base for all numbers (except I/O, which is in base 10)
		}

		in = new Scanner(System.in);
		computeInputArray(in);
	}
}
