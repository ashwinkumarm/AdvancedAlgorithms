package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q8_LengthOfLongestStreak;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST.BST;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.BinaryTree.Entry;

/**
 * Class to find the length of a longest streak of consecutive integers that
 * occur in the given array.
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */

public class LongestStreak {
	static int lastNum = 0, maxConsecutiveNums = 0, currentConsecutiveNums = 1;

	/**
	 * This method finds the longest streak using the implemented Binary Search
	 * Tree.
	 *
	 * @param A
	 */
	static int longestStreak(int[] A) {
		BST<Integer> bst = new BST<>();
		for (int i = 0; i < A.length; i++)
			bst.add(A[i]);
		lastNum = bst.min();
		getLongestStreak(bst.root);
		return maxConsecutiveNums;
	}

	/**
	 * This method performs an inorder traversal on the tree to look for
	 * consecutive numbers.
	 *
	 * @param node
	 */
	private static void getLongestStreak(Entry<Integer> node) {
		if (node != null) {
			getLongestStreak(node.left);
			checkForStreak(node);
			getLongestStreak(node.right);
		}
	}

	/**
	 * This method checks if the current element is consecutive to the previous
	 * element and updates the counter accordingly.
	 *
	 * @param node
	 */
	private static void checkForStreak(Entry<Integer> node) {
		if (node.element == lastNum + 1) {
			currentConsecutiveNums++;
			maxConsecutiveNums = Math.max(maxConsecutiveNums, currentConsecutiveNums);
		} else
			currentConsecutiveNums = 1;
		lastNum = node.element;
	}

	/**
	 * Driver method.
	 *
	 * @param args
	 */
	public static void main(String args[]) {
		int[] A = { 1, 7, 9, 4, 1, 7, 4, 2, 8, 3, 7, 1, 5, -1, 0 };
		System.out.println(longestStreak(A));
	}

}
