// Sample code for Level 2 driver for lp1

// Change following line to your group number
package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

/**
 * This class contains Main method to test LP1 Level 2
 * Operations: Division,Mod,Square root,Exponentiation(Num,Num)
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
	Num x = new Num(999);
	Num y = new Num("8");
	Num z = Num.add(x, y);
	System.out.println(z);
	Num a = Num.power(x, y);
	System.out.println(a);
	z.printList();
    }
}
