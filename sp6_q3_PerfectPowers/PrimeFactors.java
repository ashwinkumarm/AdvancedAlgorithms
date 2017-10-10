package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q3_PerfectPowers;

import java.util.PriorityQueue;

/**
 * Class to find prime factors
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class PrimeFactors {

	/**
	 * This method finds the prime factors upto n
	 * 
	 * @param arr
	 * @param n
	 */
	public static void primeFactors(int[] arr, int n) {
		PriorityQueue<Integer> pq = new PriorityQueue<>();

		for (int i : arr) {
			pq.add(i);
		}

		int prev = 0;
		while (!pq.isEmpty()) {
			int curr = pq.poll();
			// breaks the loop if greater than user given value or negative
			if (curr >= n || curr <= 0) {
				break;
			}

			if (curr != prev) {
				System.out.println(curr);
				prev = curr;
			}

			for (int i : arr) {
				pq.add(curr * i);
			}
		}

	}

	/**
	 * Main method for testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int[] a = { 3, 5, 7 };
		primeFactors(a, Integer.MAX_VALUE);
	}

}
