package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp9_q1_PairSum;

import java.util.HashMap;

/**
 * This class finds the number of pairs of elements that sums to X
 * @author Ashwin, Arun, Deepak, Haritha
 * @param <T>
 */

public class CountPairSum {

	static int howMany(int[] A, int X) {
		HashMap<Integer, Integer> freqCount = new HashMap<Integer, Integer>();
		int count = 0;

		for (int a : A) {
			if (freqCount.containsKey(Math.abs(X - a))) {
				count += freqCount.get(X-a);
			}
			if (!freqCount.containsKey(a)) {
				freqCount.put(a, 0);
			}
			int v = freqCount.get(a);
			freqCount.put(a, ++v);

		}

		return count;
	}
	
	public static void main(String[] args){
		int[] A = {3,3,4,5,3,5};
		System.out.println(howMany(A,8));
	}
}
