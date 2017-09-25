// Sample code for Level 2 driver for lp1

// Change following line to your group number
package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

/**
 * This class contains Main method to test LP1 Level 2 Operations:
 * Division,Mod,Square root,Exponentiation(Num,Num)
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class LP1L2 {
    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
	Num x = new Num("8999900346");
	Num y = new Num("-201");
	System.out.println(Num.divide(x, y));
	System.out.println(Num.mod(x, y));
	System.out.println(Num.squareRoot(x));
	System.out.println(Num.power(x, y));
	x.printList();
	y.printList();
    }
}
