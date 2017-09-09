package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

public class LP1L1 {
	public static void main(String[] args) {
		Num x = new Num(999);
		Num y = new Num("8");
		Num z = Num.add(x, y);
		System.out.println(z);
		Num a = Num.power(x, 8);
		System.out.println(a);
		z.printList();
	}
}
