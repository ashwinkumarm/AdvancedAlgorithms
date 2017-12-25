package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q6_SumPairOfElements;

import java.util.Scanner;
import java.util.TreeMap;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort.Timer;

/**
 * This class finds the number of pairs in the array that sum to the given target
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class FindPairs {

	/**
	 * Finds the number of pairs that sum to the given target using TreeMap
	 * 
	 * @param A
	 * @param X
	 * @return
	 */
	public static int howMany(int[] A, int X) {
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		Integer val;
		for (int i = 0; i < A.length; i++) {
			if ((val = map.get(A[i])) == null)
				map.put(A[i], 1);
			else
				map.put(A[i], val + 1);
		}

		int count = 0;
		for (int i = 0; i < A.length; i++) {
			if ((val = map.get(X - A[i])) != null)
				count += val;
			if (X - A[i] == A[i])
				count--;
		}
		return count / 2;
	}

	/**
	 * Main method for testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int[] arr = { 3, 3, 4, 5, 3, 5, 1 };
		int X = in.nextInt();
		System.out.println("\nFind Pairs that sum to: " + X);

		Timer t = new Timer();
		int a = howMany(arr, X);
		t.end();
		System.out.println("No of Pairs " + a);
		System.out.println(t);
		in.close();

	}

}
