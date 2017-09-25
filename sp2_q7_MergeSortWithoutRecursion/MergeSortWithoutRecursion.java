package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q7_MergeSortWithoutRecursion;

import java.util.Stack;

/**
 * Class SortableList
 *
 * <P>
 * This class implements Merge sort algorithm without recursion by maintaining
 * our own stack and simulating how the compiler implements function calls.
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class MergeSortWithoutRecursion {

	/**
	 * Iterative merge sort method.
	 *
	 * @param a
	 * @param tmp
	 * @param p
	 * @param r
	 */
	private static void mergeSort(int[] a, int[] tmp, int p, int r) {
		int q;
		StackFrame frame;
		Stack<StackFrame> stack = new Stack<>();
		stack.add(new StackFrame(false, p, r));
		System.out.println("Merge operation execution order: ");
		while (!stack.isEmpty()) {
			frame = stack.pop();
			p = frame.p;
			r = frame.r;
			q = (p + r) / 2;
			if (frame.childrenAdded == true) {
				System.out.println("Processing merge operation from index " + p + " to " + r);
				merge(a, tmp, p, q, r);
			} else {
				if (p < r) {
					stack.add(new StackFrame(true, p, r));
					stack.add(new StackFrame(false, q + 1, r));
					stack.add(new StackFrame(false, p, q));
				}
			}
		}
	}

	/**
	 * Merge Operation.
	 *
	 * @param a
	 * @param tmp
	 * @param p
	 * @param q
	 * @param r
	 */
	private static void merge(int[] a, int[] tmp, int p, int q, int r) {
		System.arraycopy(a, p, tmp, p, r - p + 1);
		int i = p, j = q + 1, k = p;
		while (i <= q && j <= r) {
			if (tmp[i] < tmp[j]) {
				a[k] = tmp[i];
				i++;
			} else {
				a[k] = tmp[j];
				j++;
			}
			k++;
		}

		while (i <= q) {
			a[k] = tmp[i];
			i++;
			k++;
		}
	}

	/**
	 * Main method used for testing.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		int a[] = { 6, 3, 5, 2, 1, 7, 4, 9, 14, 12 };
		int tmp[] = new int[10];
		mergeSort(a, tmp, 0, a.length - 1);
		System.out.print("\nSorted array: ");
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i]);
			System.out.print(" ");
		}
	}
}
