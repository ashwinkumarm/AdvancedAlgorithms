package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort;

//import java.util.Random;

/**
 * Class SortAlgos
 * 
 * <P>
 * This class calls all the sort algorithms
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class SortAlgos {
	public static void main(String args[]) {
		int[] arraySizes = { 25000, 37500, 50000, 80500, 100000, 250000, 300000, 500000 };

		for (int n = 0; n < arraySizes.length; n++) {

			System.out.println("\nFor arraySize " + arraySizes[n] + ":");

			// For wrapper class Integer type
			Integer[] integerArr = new Integer[arraySizes[n]];
			for (int i = 0; i < arraySizes[n]; i++) {
				integerArr[i] = new Integer(i + 1);
			}

			// For basic int type
			int[] arr = new int[arraySizes[n]];
			for (int i = 0; i < arraySizes[n]; i++) {
				arr[i] = i + 1;
			}

			System.out.println("Array Creation Completed");
			System.out.println("Sorting....");

			Shuffle.shuffle(arr);
			Timer t = new Timer();
			MergeSortInt.mergeSort(arr, new int[arr.length]);
			System.out.println("Time and Memory taken by Merge Sort(int):\n" + t.end());

			Shuffle.shuffle(integerArr);
			t.start();
			InsertionSort.nSquareSort(integerArr);
			System.out.println("Time and Memory taken by Insertion Sort(Generic):\n" + t.end());

			t.start();
			MergeSortGeneric.mergeSort(integerArr, new Integer[integerArr.length]);
			System.out.println("Time and Memory taken by Merge Sort(Generic):\n" + t.end());

		}

	}

}
