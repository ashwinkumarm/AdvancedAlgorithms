package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q6_SumPairOfElements;

import java.util.Scanner;
import java.util.TreeMap;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort.Timer;

public class FindPairs {

	public static int howMany(int[] A, int X) {
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		for (int i = 0; i < A.length; i++) {
			if (!map.containsKey(A[i]))
				map.put(A[i], 0);
			map.put(A[i], map.get(A[i]) + 1);

		}
		int count = 0;
		for (int i = 0; i < A.length; i++) {
			if (map.get(X - A[i]) != null)
				count += map.get(X - A[i]);
			if (X - A[i] == A[i])
				count--;
		}

		return count / 2;
	}

	public static void generateArray(int size, int[] arr, Scanner in) {
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		// int size = in.nextInt();
		// int[] arr = new int[size];
		// generateArray(size, arr, in);
		int[] arr = { 3, 3, 4, 5, 3, 5 };
		int X = in.nextInt();
		System.out.println("\n Find Pairs that sum to: " + X);

		Timer t = new Timer();
		int a = howMany(arr, X);
		t.end();
		System.out.println("No of Pairs " + a);
		System.out.println(t);
		in.close();

	}

}
