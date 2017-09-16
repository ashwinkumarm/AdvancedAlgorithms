package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

public class LP1L1 {
	public static void main(String[] args) {
		// BigInteger b = new BigInteger();
		Num x = new Num(40);
		Num y = new Num(50);
		//System.out.println(x);
		//Num y1 = new Num("-867576876924692");
		// System.out.println(y1.digits);
		// Num z = Num.add(x,y); //Ans should be 867577868167041
		//Num s = Num.add(x, y); // Ans shld be 867575885682343
		Num d = Num.product(x, y);
		System.out.println(d);

		//System.out.println(Num.divideByTwo(x));
		/**
		 * Test Input add 991242349, -867576876924692 -867575885682343
		 * -991242349, 867576876924692 867575885682343 -991242349,
		 * -867576876924692 -867577868167041 991242349, 867576876924692
		 * 867577868167041
		 *
		 * subtract 991242349, 867576876924692 -867575885682343 991242349,
		 * -867576876924692 867577868167041 -991242349, 867576876924692
		 * -867577868167041 -991242349, -867576876924692 867575885682343
		 *
		 **/

	}
}
