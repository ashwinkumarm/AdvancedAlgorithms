package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort;

/**
 * Class MergeSortGeneric
 * 
 * <P>
 * This class performs merge sort on a generic array of size ranging from 1M -
 * 16M
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class MergeSortGeneric<T> {

	/**
	 * Gives us the sorted array of elements
	 * 
	 * @param arr
	 *            generic array which has the original content
	 * @param tmp
	 *            temporary generic array
	 */
	public static <T extends Comparable<? super T>> void mergeSort(T[] arr, T[] tmp) {
		divide(arr, tmp, 0, arr.length - 1);
	}

	/**
	 * Divides the array into left and right and calls the merge method
	 * 
	 * @param arr
	 *            generic array which has the original content
	 * @param tmp
	 *            temporary generic array
	 * @param l
	 *            leftmost element index
	 * @param r
	 *            rightmost element index
	 */
	private static <T extends Comparable<? super T>> void divide(T[] arr, T[] tmp, int l, int r) {
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
	 *            generic array which has the original content
	 * @param tmp
	 *            temporary generic array
	 * @param l
	 *            leftmost element index
	 * @param m
	 *            middle element index
	 * @param r
	 *            rightmost element index
	 */
	private static <T extends Comparable<? super T>> void merge(T[] arr, T[] tmp, int l, int m, int r) {
		// copies all the element to temporary array
		System.arraycopy(arr, l, tmp, l, r - l + 1);

		int i = l, j = m + 1, k = l;
		// compares the element in the left and right half and adds it to the original
		// array
		while (i <= m && j <= r) {
			if (tmp[i].compareTo(tmp[j]) <= 0) {
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
}
