package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q8_SelectUsingOurOwnHeap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort.Shuffle;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort.Timer;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q5_BinaryHeapSort.BinaryHeap;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.ArrayIterator;

public class SelectComparison {

	/**
	 * Select K largest number using Java's priorityQueue (Min Heap)
	 *
	 * @param <T>
	 *
	 * @param A
	 *            : Integer[] array
	 * @param k
	 *            : int k number of largest elements
	 * @return : Integer[] array with k largest elements
	 */
	public static <T> Object[] javaPriorityQueueMinHeap(T A[], int k, Comparator<T> comparator) {

		PriorityQueue<T> queue = new PriorityQueue<>(k, comparator);
		ArrayIterator<T> it = new ArrayIterator<>(A);

		while (k > 0) {
			if (it.hasNext()) {
				queue.add(it.next());
			} else {
				return queue.toArray();
			}
			k--;
		}

		while (it.hasNext()) {
			T x = it.next();
			if (comparator.compare(x, queue.peek()) > 0) {
				queue.poll();
				queue.add(x);
			}

		}
		return queue.toArray();
	}

	/**
	 * Select K largest number using our own priority queue (Min Heap) with
	 * replace() method.
	 *
	 * @param <T>
	 *
	 * @param A
	 *            : Integer[] array
	 * @param k
	 *            : int k number of largest elements
	 * @return : Integer[] array with k largest elements
	 */
	public static <T> T[] priorityQueueMinHeapWithReplace(T A[], int k, Comparator<T> comparator) {

		BinaryHeap<T> queue = new BinaryHeap<>(k, comparator);
		ArrayIterator<T> it = new ArrayIterator<>(A);

		while (k > 0) {
			if (it.hasNext()) {
				queue.add(it.next());
			} else {
				return queue.toArray();
			}
			k--;
		}

		while (it.hasNext()) {
			T x = it.next();
			if (comparator.compare(x, queue.peek()) > 0)
				queue.replace(x);
		}
		return queue.toArray();
	}

	/**
	 * Helper method for generating increasing order array.
	 *
	 * @param arr
	 * @param size
	 */
	public static void generateArray(Integer[] arr, int size) {
		for (int i = 1; i <= size; i++)
			arr[i-1] = i;
	}

	/**
	 * Main method.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int size = in.nextInt();
		int k = in.nextInt();
		Integer[] arr = new Integer[size];

		generateArray(arr, size);
		Shuffle.shuffle(arr);

		Timer t = new Timer();
		Object[] result1 = javaPriorityQueueMinHeap(arr, k, Comparator.<Integer>naturalOrder());
		t.end();

		System.out.println("Random Ordered Input:\n");
		System.out.println("Java's Priority Queue :");
		System.out.println(Arrays.toString(result1));
		System.out.println(t);
		System.out.println("-------------------------");

		generateArray(arr, size);
		Shuffle.shuffle(arr);

		t.start();
		Object[] result2 = priorityQueueMinHeapWithReplace(arr, k, Comparator.<Integer>naturalOrder());
		t.end();

		System.out.println("Priority Queue Implementation with replace():");
		System.out.println(Arrays.toString(result2));
		System.out.println(t);
		System.out.println("___________________________\n");

		generateArray(arr, size);

		t.start();
		Object[] result3 = javaPriorityQueueMinHeap(arr, k, Comparator.<Integer>naturalOrder());
		t.end();

		System.out.println("Increasing ordered Input:\n");
		System.out.println("Java's Priority Queue :");
		System.out.println(Arrays.toString(result3));
		System.out.println(t);
		System.out.println("-------------------------");

		generateArray(arr, size);

		t.start();
		Object[] result4 = priorityQueueMinHeapWithReplace(arr, k, Comparator.<Integer>naturalOrder());
		t.end();

		System.out.println("Priority Queue Implementation with replace():");
		System.out.println(Arrays.toString(result4));
		System.out.println(t);
		System.out.println("___________________________");
		in.close();
	}
}
