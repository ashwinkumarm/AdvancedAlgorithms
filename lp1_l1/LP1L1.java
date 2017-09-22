package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;


/**
 * This class contains Main method to test LP1 Level 1
 * Operations: Addition, Subtraction,Multiplication,Division,Exponentiation(Num,long)
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class LP1L1 {
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		
		Num x = new Num("8999909");
		Num y = new Num("200");
		System.out.println(x);
		System.out.println(y);
		//System.out.println(Num.product(x, y));
//		System.out.println(Num.add(x, y));
//		System.out.println(Num.subtract(x, y));
		System.out.println(Num.power(x, y));
		x.printList();
		y.printList();

	}
}
