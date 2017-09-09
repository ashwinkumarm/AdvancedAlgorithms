package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q7_MergeSortWithoutRecursion;

import java.util.Stack;

public class MergeSortWithoutRecursion {
	private static void mergeSort(int[] a, int[] tmp, int p, int r) {
		int q;
		Stack<StackFrame> stack = new Stack<>();
		while (p < r) {
			q = (p + r) / 2;
			r = q;
			stack.add(new StackFrame(a, tmp, p, q));
			// stack.add(new StackFrame(a, tmp, q+1, r));
			// merge(a, tmp, p, q, r);
		}
		while (p < r) {
			q = (p + r) / 2;
			r = q;
			stack.add(new StackFrame(a, tmp, p, q));
			// stack.add(new StackFrame(a, tmp, q+1, r));
			// merge(a, tmp, p, q, r);
		}
	}

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

	public static void main(String[] args) {
		int a[] = { 6, 3, 5, 2, 1, 7, 4, 9 };
		int tmp[] = new int[8];
		mergeSort(a, tmp, 0, a.length - 1);
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}
}
