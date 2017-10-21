package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q7_ElementsExactlyOnce;

import java.util.Arrays;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q5_BSTMap.BSTMap;

/**
 * Class to create an array that has those elements of the input array that
 * occur exactly once, in the same order in which they appear in it.
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class ElementsExactlyOnce {
	/**
	 * This method returns an array that has those elements of the input array
	 * that occur exactly once.
	 *
	 * @param A
	 */
	static <T extends Comparable<? super T>> T[] exactlyOnce(T[] A) {
		BSTMap<T, Integer> map = new BSTMap<T, Integer>();
		int n = 0;
		for (int i = 0; i < A.length; i++) {
			if (!map.containsKey(A[i])) {
				n++;
				map.put(A[i], 1);
			} else {
				n--;
				map.put(A[i], map.get(A[i]) + 1);
			}
		}

		T[] B = (T[]) new Comparable[n];
		n = 0;
		for (int i = 0; i < A.length; i++) {
			if (map.get(A[i]).equals(1)) {
				B[n] = A[i];
				n++;
			}
		}
		return B;
	}

	/**
	 * Driver method.
	 *
	 * @param args
	 */
	public static void main(String args[]) {
		Integer[] A = { 6, 3, 4, 5, 3, 5 };
		System.out.println(Arrays.toString(exactlyOnce(A)));
	}
}
