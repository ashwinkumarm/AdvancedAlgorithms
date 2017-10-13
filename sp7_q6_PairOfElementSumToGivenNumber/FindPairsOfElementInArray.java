package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q6_PairOfElementSumToGivenNumber;

import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort.Timer;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp4_q4_BinarySearch.BinarySearch;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp5_q2_QuickSortDualPivotPartition.DualPivotSort;

public class FindPairsOfElementInArray {

	public static int findHowMany(int[] A, int X) {

		int pairs = 0;
		for (int i = 0; i < A.length - 1; i++) {
			int diff = X - A[i];
			int occurence = BinarySearch.findNoOfOccurences(A, i + 1, A.length - 1, diff);
			if (occurence > 0) {
				pairs += occurence;
			}
		}
		return pairs;
	}

	public static int howMany(int[] A, int X) {
		DualPivotSort.dpQuickSort(A, 0, A.length - 1);
		return findHowMany(A, X);
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
		int[] arr = { 1, 1, 1, 1, 1 };
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
