package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp8_q7_PermutationAndCombination;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Timer;

public class PermutationAndCombination<T> {
	static int k, n, count;
	T[] A;
	T[] chosen; // used for combination
	static int VERBOSE = 0;

	public PermutationAndCombination(T[] a) {
		super();
		A = a;
		chosen = (T[]) new Object[k];
	}

	// Already selected: A[0.....d-1]. Need to select c more elements from
	// A[d...n-1], where d=k-c.
	void permute(int c) {
		if (c == 0) {
			// visit permutation in A[0...k-1]
			if (VERBOSE == 1) {
				System.out.print("(");
				for (int i = 0; i < k - 1; i++)
					System.out.print(A[i] + ",");
				System.out.print(A[k] + ") ");
			}
			count++;
		} else {
			int d = k - c;
			T tmp;
			permute(c - 1);
			for (int i = d + 1; i < n; i++) {
				tmp = A[d];
				A[d] = A[i];
				A[i] = tmp;
				permute(c - 1);
				A[i] = A[d];
				A[d] = tmp;
			}
		}
	}

	// Choose c more items from A[i...n-1]. Already selected: chosen[0...k-c-1].
	/**
	 * @param i
	 * @param c
	 */
	void combination(int i, int c) {
		if (c == 0) {
			// visit permutation in A[0...k-1]
			if (VERBOSE == 1) {
				System.out.print("(");
				for (int j = 0; j < k - 1; j++)
					System.out.print(chosen[j] + ",");
				System.out.print(chosen[k - 1] + ") ");
			}
			count++;
		} else {
			chosen[k - c] = A[i]; // choose A[i]
			combination(i + 1, c - 1);
			if (n - i > c)
				combination(i + 1, c); // Skip A[i] only if there are enough
										// elements leftF
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		if (args.length > 1) {
			VERBOSE = Integer.parseInt(args[1]);
		}

		VERBOSE = 1;
		Integer arr[] = { 1, 2, 3, 4 };
		k = 2;
		n = arr.length;
		PermutationAndCombination<Integer> pc = new PermutationAndCombination<>(arr);
		Timer timer = new Timer();
		System.out.println("Permutations: ");
		pc.permute(k);
		System.out.println("\nNumber of Permutations: " + count + "\n");
		count = 0;
		System.out.println("Combinations: ");
		pc.combination(0, k);
		System.out.println("\nNumber of Combinations: " + count + "\n");
		timer.end();
		System.out.println(timer);
		in.close();
	}
}
