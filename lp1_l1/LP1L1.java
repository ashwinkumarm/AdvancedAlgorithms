package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

public class LP1L1 {
	public static void main(String[] args) {
		// BigInteger b = new BigInteger();
		Num x = new Num(26252);
		Num y = new Num(223);
		System.out.println(Num.product(x, y));
		// Num y1 = new Num("-867576876924692");
		// System.out.println(y1.digits);
		// Num z = Num.add(x,y); //Ans should be 867577868167041
		/*System.out.println(Num.add(x, y));
		System.out.println(Num.divideMagnitudeByTwo(x));
		System.out.println(Num.divide(x, y));
		System.out.println(Num.mod(x, y));
		System.out.println(Num.squareRoot(x));
		System.out.println(Num.subtract(x, y));*/
		System.out.println(Num.power(x, y));



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
