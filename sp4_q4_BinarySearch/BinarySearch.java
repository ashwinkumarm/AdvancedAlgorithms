package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp4_q4_BinarySearch;

/**
 * This class performs the binary search recursively to find the index of the
 * largest number which is less than or equal to target(x)
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class BinarySearch {

	/**
	 * Performs the binary search on the given array
	 * 
	 * @param arr
	 * @param x
	 * @return
	 */
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
		// Compare middle element of A[p..r] to x to decide which half of the
		// array to search
		if (p <= r) {

			// If last element is smaller than x, return r
			if (A[r].compareTo(x) < 0)
				return r;

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
	
	public static int recursiveBinarySearch(int[] A, int p, int r, int x) {
		// Compare middle element of A[p..r] to x to decide which half of the array to search
		if(p <= r) {
		    int q = (p+r)/2;
		    if(A[q] < x) {  // A[q] is too small, x is not in left half
			return recursiveBinarySearch(A, q+1, r, x);
		    } else if (A[q] == x) {  // x found
			return q;
		    } else { // A[q] > x, so x is not in the right half
			return recursiveBinarySearch(A, p, q-1, x);
		    }
		} else { // empty array, return false
		    return -1;
		}
	}
	
	public  static int findNoOfOccurences(int[] arr, int p, int r, int x) {
		int index = recursiveBinarySearch(arr,p, r, x);
		int occurence = 0;
		if(index != -1){
			occurence++;
			if(index !=p && index != arr.length-1){
				if(arr[index-1] == x ){
					while(index > p && arr[index-1] == x){
					occurence++;
					index--;
					}
				}
				else if (index < r){
					while(index < r && arr[index+1] == x){
					occurence++;
					index++;
					
					}
				}
			}
		}
		
		return occurence;
	}
	
	/**
	 * Main method for testing
	 * @param args
	 */
	public static void main(String[] args) {

		int a[] = { 1, 2, 3, 6, 7, 9, 10, 14, 15, 16, 18 };
		Integer[] arr = new Integer[a.length];

		for (int i = 0; i < a.length; i++) {
			arr[i] = new Integer(a[i]);
		}
		int x = 19;
		int index = binarySearch(arr, x);
		if (index != -1) {
			System.out.println("Index of the larget number less than or equal to " + x + ": " + index);
		} else
			System.out.println("Index not found");
	}
}