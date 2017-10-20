package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q7_ElementsExactlyOnce;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST.BinarySearchTree;

/**
 * Class to find the elements of the given array that occur exactly once.
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class ElementsExactlyOnce {

	/**
	 * This method finds the longest streak using the implemented Binary Search
	 * Tree.
	 *
	 * @param A
	 */
	static <T extends Comparable<? super T>> T[] exactlyOnce(T[] A) {
		BinarySearchTree<T> bst = new BinarySearchTree<>();
		T[] B = (T[]) new Object[A.length];
		for (int i = 0; i < A.length; i++)
			bst.add(A[i]);
		//TODO : Incomplete
		return B;
	}

	/**
	 * Driver method.
	 *
	 * @param args
	 */
	public static void main(String args[]) {
		Integer[] A = { 1, 7, 9, 4, 1, 7, 4, 2, 8, 3, 7, 1, 5, -1, 0 };
		System.out.println(exactlyOnce(A));
	}
}
