package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp4_q4_BinarySearch;

public class BinarySearch {

	public static <T extends Comparable<? super T>> int binarySearch(T[] arr, T x) {
		return recursiveBinarySearch(arr, 0, arr.length - 1, x);
	}

	/**
	 * Recursive method which runs binary search on a sorted array and return the
	 * index of the largest element that is less than or equal to x such that arr[i]
	 * <= x < arr[i+1].
	 *
	 * @param A:
	 *            array. Precondition: A is sorted. If A is not sorted, behavior of
	 *            procedure is arbitrary
	 * @param p:
	 *            int : left index of subarray of A
	 * @param r:
	 *            int : right index of subarray of A
	 * @param x:
	 *            element being searched
	 * @return index of the largest element that is less than or equal to x such
	 *         that arr[i] <= x < arr[i+1]. else return -1
	 */

	public static <T extends Comparable<? super T>> int recursiveBinarySearch(T[] A, int p, int r, T x) {
		//If last element is smaller than x, return r
		if (A[r].compareTo(x) < 0)
			return r;
		// Compare middle element of A[p..r] to x to decide which half of the
		// array to search
		if (p <= r) {
			int q = (p + r) / 2;
			int cmp1 = A[q].compareTo(x);
			int cmp2 = A[q + 1].compareTo(x);
			if (cmp1 == 0 || (cmp1 < 0 && cmp2 > 0)) { // A[q] is less than or equal to x and A[q+1] is greater than x
				return q; // return index q
			} else if (cmp1 < 0) { // A[q] is too small, x is not in left half
				return recursiveBinarySearch(A, q + 1, r, x);
			} else { // A[q] > x, so x is not in the right half
				return recursiveBinarySearch(A, p, q - 1, x);
			}
		} else { // if p crosses r , return -1
			return -1;
		}
	}

	public static void main(String[] args) {

		int a[] = { 1, 2, 3, 6, 7, 9, 10, 14, 15, 16, 18 };
		Integer[] iarr = new Integer[a.length];

		for (int i = 0; i < a.length; i++) {
			iarr[i] = new Integer(a[i]);
		}
		int x = 17;
		int index = binarySearch(iarr, x);
		if (index != -1) {
			System.out.println("Index of the larget number less than or equal to " + x + ": " + index);
		} else
			System.out.println("Index not found");
	}
}