package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp4_q6_MissingKnumbers;

public class MissingKNumbers {

	public static void main(String[] args) {
		int a[] = { 1, 2, 3, 6, 7, 9 };
		findMissing(a, 0, a.length - 1);
	}

	private static void findMissing(int[] a, int p, int r) {
		if (p < r) {
			if (a[r] - a[p] == r - p)
				return;

			if (r - p == 1) {
				int i = ++a[p];
				while (i < a[r])
					System.out.println(i++);
			}
			int q = (p + r) / 2;
			findMissing(a, p, q);
			findMissing(a, q, q+1);
			findMissing(a, q + 1, r);
		}
	}
}
