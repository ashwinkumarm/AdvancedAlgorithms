package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q8_LengthOfLongestStreak;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp5_q2_QuickSortDualPivotPartition.DualPivotSort;

public class LongestStreak {
	static int longestStreak(int[] A) {
		if (A.length == 0 || A == null)
			return 0;
		DualPivotSort.dpQuickSort(A, 0, A.length - 1);
		int lastNum = A[0];
		int maxConsecutiveNums = 0;
		int currentConsecutiveNums = 1;
		for (int i = 1; i < A.length; i++) {
			if (A[i] == lastNum + 1) {
				currentConsecutiveNums++;
				maxConsecutiveNums = Math.max(maxConsecutiveNums, currentConsecutiveNums);
			} else
				currentConsecutiveNums = 1;
			lastNum = A[i];
		}
		return Math.max(maxConsecutiveNums, currentConsecutiveNums);
	}

	public static void main(String args[]) {
		int[] A = { 1, 7, 9, 4, 1, 7, 4, 8, 7, 1 };
		System.out.println(longestStreak(A));
	}

}
