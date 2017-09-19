// Starter code for Level 4 driver for lp1

// Change following line to your group number
package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;
import java.util.HashMap;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Tokenizer;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Tokenizer.Token;


public class LP1L4 {
	static InputString[] inputArray;
	static Num[] variableMap = new Num[26];
	static HashMap<Integer,Integer> LineNoForLabel = new HashMap<>();
	
	//the jump can go backward or forward. Since forward is possible, we need to read the entire inp first. Read the entire inp
	//store t n ob array. obj class will have varible to store the entire infix expression. helper methods for converting and evaluating.
	//Have variable in the class to say if its a just a assign, expression or loop conditions
	//Have hashmap for storing label and line number too. 
	
	//Read the entire input again from first line. If t s not a loop condition, then just update the values upto the specific line and 
	//move to the next line. For updating we need to update for the specific object plus we can have an num array like in lp3 and keep updating 
	//so that referencing the value is easier. 
	//if its a loop conditon, we can have an helper function which iterates from specific label, retrieved from hashmap and it runs from the specific
	//line number to the line number of the current line. and returns the value of loop variable. Its basically a loop where that value is greater than zero. 
	
	static void computeInputArray(Scanner in) throws Exception {
		int i = 0;
		StringBuilder expression = new StringBuilder();
		while (in.hasNext()) {
			String word = in.next();
			Token token = Tokenizer.tokenize(word);
			InputString inp = new InputString();
			inp.setLineNo(i);
			i++;
			if (token.equals(Token.EOL)) {
				inputArray[i] = new InputString(inp);
				inp = new InputString();
			} else if (token.equals(Token.NUM)) {
				LineNoForLabel.put(Integer.parseInt(word), i);
				inp.setLabel(word);
			} else if (token.equals(Token.VAR)) {
				inp.setVariable(word);
			} else if (token.equals(Token.EQ)) {
				while (in.hasNext()) {
					word = in.next();
					if (!word.equals(Token.EOL)){
						expression.append(word);
					}
					else {
						if (expression.toString().equals(Token.NUM)) {
							inp.setValue(new Num(expression.toString()));
							inp.setAssgn(true);
						} else {
							 inp.setInfixExpression(expression.toString());
							 inp.setExpression(true);
						}
						break;
					}
				}
				expression = new StringBuilder();
			} else {
				while (in.hasNext()) {
					word = in.next();
					if (!word.equals(Token.EOL)) {
						expression.append(word);
					}else{
						if(expression.toString().equals(Token.NUM)){
							inp.setNz(Integer.parseInt(word));
						}else{
							String[] labels = expression.toString().split(":");
							inp.setNz(Integer.parseInt(labels[0]));
							inp.setZr(Integer.parseInt(labels[1]));
						}
					}

				}
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
