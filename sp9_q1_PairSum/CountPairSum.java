package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp9_q1_PairSum;

import java.util.HashMap;

/**
 * This class finds the number of pairs of elements that sums to X
 * @author Ashwin, Arun, Deepak, Haritha
 */

public class CountPairSum {

	static int howMany(int[] A, int X) {
		HashMap<Integer, Integer> freqCount = new HashMap<Integer, Integer>();
		int count = 0;
		Integer val;
		for (int a : A) {

			if ((val = freqCount.get(Math.abs(X - a))) != null) {
				count += val;
			}

			if ((val = freqCount.get(a)) == null) {
				freqCount.put(a, 1);
			} else {
				freqCount.put(a, ++val);
			}
		}

		return count;
	}

	public static void main(String[] args) {
		int[] A = { 3, 3, 4, 5, 3, 5 };
		System.out.println(howMany(A, 8));
	}
}
