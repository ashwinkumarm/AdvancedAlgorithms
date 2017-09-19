package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp4_q6_MissingKnumbers;

/**
 * This class finds the k missing numbers in a sorted array of n distinct
 * integers, starting at 1 and ending with n+k
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */

public class MissingKNumbers {

	/**
	 * Main method used for testing.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		int a[] = { 1, 2, 3, 6, 7, 9, 10, 14, 15, 16, 18 };
		System.out.print("The missing elements are: ");
		findMissing(a, 0, a.length - 1);
	}

	/**
	 * Recursive routine to find the missing elements. There are lg(n) steps
	 * where each step does some extra work in the form of findMissing(a, q, q +
	 * 1). This extra work sums up to a constant running time of k. Thus, the
	 * total running time is O(lg n + k).
	 *
	 * @param a
	 * @param p
	 * @param r
	 */
	private static void findMissing(int[] a, int p, int r) {
		if (p < r) {
			if (a[r] - a[p] == r - p)
				return;

			if (r - p == 1) {
				int i = a[p] + 1;
				while (i < a[r])
					System.out.print(i++ + " ");
				return;
			}
			int q = (p + r) / 2;
			findMissing(a, p, q);
			findMissing(a, q, q + 1);
			findMissing(a, q + 1, r);
		}
	}
}
