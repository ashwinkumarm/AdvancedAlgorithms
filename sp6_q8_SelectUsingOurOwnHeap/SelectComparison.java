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
	 * @param A
	 *            : Integer[] array
	 * @param k
	 *            : int k number of largest elements
	 * @return : Integer[] array with k largest elements
	 */
	public static Object[] javaPriorityQueueMinHeap(Integer A[], int k) {

		PriorityQueue<Integer> queue = new PriorityQueue<>(k);
		ArrayIterator<Integer> it = new ArrayIterator<>(A);

		while (k > 0) {
			if (it.hasNext()) {
				queue.add(it.next());
			} else {
				return queue.toArray();
			}
			k--;
		}

		while (it.hasNext()) {
			int x = it.next();
			if (queue.peek() < x) {
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
	 * @param A
	 *            : Integer[] array
	 * @param k
	 *            : int k number of largest elements
	 * @return : Integer[] array with k largest elements
	 */
	public static Object[] priorityQueueMinHeapWithReplace(Integer A[], int k) {

		BinaryHeap<Integer> queue = new BinaryHeap<Integer>(k, Comparator.<Integer>naturalOrder());
		ArrayIterator<Integer> it = new ArrayIterator<>(A);

		while (k > 0) {
			if (it.hasNext()) {
				queue.add(it.next());
			} else {
				return queue.toArray();
			}
			k--;
		}

		while (it.hasNext()) {
			int x = it.next();
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
		int j = size;
		for (int i = 0; i < size; i++) {
			arr[i] = j;
			j--;
		}
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
		Object[] result1 = javaPriorityQueueMinHeap(arr, k);
		t.end();

		System.out.println("Java's Priority Queue :");
		System.out.println(Arrays.toString(result1));
		System.out.println(t);
		System.out.println("-------------------------");

		generateArray(arr, size);
		Shuffle.shuffle(arr);

		t.start();
		Object[] result2 = priorityQueueMinHeapWithReplace(arr, k);
		t.end();

		System.out.println("Priority Queue Implementation with replace():");
		System.out.println(Arrays.toString(result2));
		System.out.println(t);
		System.out.println("___________________________");
		in.close();
	}
}
