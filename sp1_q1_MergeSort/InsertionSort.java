package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort;

/**
 * Class InsertionSort
 * 
 * <P>
 * This class performs insertion sort on a generic array of size ranging from 1M
 * - 16M
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class InsertionSort {

	/**
	 * nSquareSort method gives us the sorted array of elements
	 * 
	 * @param arr
	 *            generic array which has the original content
	 */
	public static <T extends Comparable<? super T>> void nSquareSort(T[] arr) {
		for (int i = 1; i < arr.length; i++) {
			T key = arr[i];
			int j = i - 1;
			while (j > 0 && arr[j].compareTo(key) > 0) {
				arr[j + 1] = arr[j];
				j = j - 1;
			}
			arr[j + 1] = key;
		}
	}
}
