package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q5_BinaryHeapSort;

import java.util.Comparator;
import java.util.Random;

/**
 * Driver program to test Binary Heap and heap sort.
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class SP6Q5Driver {

	/**
	 * main method.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// Integer a[] = { 45, 89, 21, 6, 3, 54, 57, 2, 11, 7, 43, 9, 99 };
		Random rand = new Random();
		Integer a[] = new Integer[100];
		for (int i = 0; i < 50; i++)
			a[i] = rand.nextInt(100);

		MinHeapComparator<Integer> minComp = new MinHeapComparator<>();
		MaxHeapComparator<Integer> maxComp = new MaxHeapComparator<>();

		// Creating a minHeap on the given array
		new BinaryHeap<>(a, minComp, 50);
		System.out.print("Min Heap: ");
		printArray(a);

		// Creating a maxHeap on the given array
		new BinaryHeap<>(a, maxComp, 50);
		System.out.print("Max Heap: ");
		printArray(a);

		// Creating a random array filled to capacity
		for (int i = 0; i < 100; i++)
			a[i] = rand.nextInt(100);

		System.out.print("Array before Sorting: ");
		printArray(a);

		// Sorting the array in ascening order using maxHeap
		BinaryHeap.heapSort(a, maxComp);
		System.out.print("Sorted Array in Ascending Order: ");
		printArray(a);

		// Sorting the array in decending order using minHeap
		BinaryHeap.heapSort(a, minComp);
		System.out.print("Sorted Array in Descending Order: ");
		printArray(a);
	}

	/**
	 * Method tp print the contents of the array.
	 *
	 * @param a
	 */
	private static void printArray(Integer[] a) {
		for (Integer element : a)
			System.out.print(element + " ");
		System.out.println("\n");
	}

	/**
	 * Comparator to create Min Heap.
	 *
	 * @param <T>
	 */
	static class MinHeapComparator<T extends Comparable<T>> implements Comparator<T> {

		@Override
		public int compare(T o1, T o2) {
			return o1.compareTo(o2);
		}
	}

	/**
	 * Comparator to create Max Heap.
	 *
	 * @param <T>
	 */
	static class MaxHeapComparator<T extends Comparable<T>> implements Comparator<T> {

		@Override
		public int compare(T o1, T o2) {
			if (o1.compareTo(o2) < 0)
				return 1;
			else if (o1.compareTo(o2) > 0)
				return -1;
			else
				return 0;
		}
	}
}
