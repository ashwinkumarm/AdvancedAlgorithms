package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort;

/**
 * Class MergeSortInt
 * 
 * <P>
 * This class performs merge sort on basic 'int' type array of size ranging from
 * 1M - 16M
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class MergeSortInt {

	/**
	 * Gives us the sorted array of elements
	 * 
	 * @param arr
	 *            int type array which has the original content
	 * @param tmp
	 *            temporary int array
	 */
	public static void mergeSort(int[] arr, int[] tmp) {
		divide(arr, tmp, 0, arr.length - 1);
	}

	/**
	 * Divides the array into left and right and calls the merge method
	 * 
	 * @param arr
	 *            int type array which has the original content
	 * @param tmp
	 *            temporary int array
	 * @param l
	 *            leftmost element index
	 * @param r
	 *            rightmost element index
	 */

	private static void divide(int[] arr, int[] tmp, int l, int r) {
		if (l < r) {
			int mid = (l + r) / 2;

			divide(arr, tmp, l, mid);
			divide(arr, tmp, mid + 1, r);

			merge(arr, tmp, l, mid, r);
		}
	}

	/**
	 * Merge method which sorts the given array.
	 * 
	 * @param arr
	 *            int type array which has the original content
	 * @param tmp
	 *            temporary int array
	 * @param l
	 *            leftmost element index
	 * @param m
	 *            middle element index
	 * @param r
	 *            rightmost element index
	 */
	private static void merge(int[] arr, int[] tmp, int l, int m, int r) {
		// copies all the element to temporary array
		System.arraycopy(arr, l, tmp, l, r - l + 1);

		int i = l, j = m + 1, k = l;
		// compares the element in the left and right half and adds it to the original
		// array
		while (i <= m && j <= r) {
			if (tmp[i] <= tmp[j]) {
				arr[k] = tmp[i];
				i++;
			} else {
				arr[k] = tmp[j];
				j++;
			}
			k++;
		}
		// copies the rest of first half
		while (i <= m) {
			arr[k] = tmp[i];
			k++;
			i++;
		}

	}
	
	
	public static void main(String[] args){
		int[] arr = {14,7,3,12,9,11,6,2};
		int[] t = new int[arr.length];
		mergeSort(arr,t);
	}
}
