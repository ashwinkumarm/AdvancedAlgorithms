package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp5_q5_ExternalSorting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class ExternalSortingOnFile {

	/**
	 * Performs the insertion sort on the given array
	 *
	 * @param A
	 * @param l
	 * @param r
	 */
	private static void insertionSort(int[] A, int l, int r) {
		int i = l + 1;
		int key, j;
		while (i <= r) {
			key = A[i];
			j = i - 1;
			while (j >= 0 && A[j] > key) {
				A[j + 1] = A[j];
				j -= 1;
			}
			A[j + 1] = key;
			i++;
		}
	}

	/**
	 * Recursively divides the array and performs the insertion sort for some
	 * threshold or else merge operation by sorting the arrays
	 *
	 * @param A
	 * @param B
	 * @param l
	 * @param r
	 */
	private static void mergeSortVersion4(int[] A, int[] B, int l, int r) {
		if ((r - l) < 5) {
			insertionSort(A, l, r);
		} else {
			int mid = (l + r) / 2;
			mergeSortVersion4(B, A, l, mid);
			mergeSortVersion4(B, A, mid + 1, r);
			merge4(B, A, l, mid, r);
		}
	}

	/**
	 * Merge method sorts the given array without copying the input array to tmp
	 * array
	 *
	 * @param A
	 * @param B
	 * @param l
	 * @param mid
	 * @param r
	 */
	private static void merge4(int[] A, int[] B, int l, int mid, int r) {
		int i = l, j = mid + 1;
		for (int k = l; k <= r; k++) {
			if (j > r || (i <= mid && A[i] <= A[j]))
				B[k] = A[i++];
			else
				B[k] = A[j++];
		}
	}

	/**
	 * Quick sort recursive routine.
	 *
	 * @param a
	 * @param i
	 * @param j
	 */
	private static void quickSort(int[] a, int p, int r) {
		if (p < r) {
			int q = partition(a, p, r);
			quickSort(a, p, q - 1);
			quickSort(a, q + 1, r);
		}
	}

	/**
	 * Helper method. Rearranges A[p....r] such that A[p....q-1] <= X = A[q] <
	 * A[q+1....r]. The element X is called as pivot element. How to choose the
	 * pivot? It has been proven that determiniistic choices always performs
	 * bad. A good choice is to choose X uniformly at random from A[p...r].
	 *
	 * Loop Invariant - A[p..i] <= X; A[i+1..j-1] > X; A[j..r-1] - unprocessed
	 *
	 * @param A
	 * @param p
	 * @param r
	 */
	static int partition(int A[], int p, int r) {
		int i = randomIndexGenerator(p, r);
		int x = A[i];
		swap(i, r, A);
		i = p - 1;
		for (int j = p; j <= r - 1; j++)
			if (A[j] <= x) {
				i++;
				swap(i, j, A);
			}
		swap(i + 1, r, A);
		// A[ p..i ] <= x, A[i+1] = x, A[i+2..r ] > x
		return i + 1;
	}

	/**
	 * Method to generate randon Index from the array
	 *
	 * @param fromIndex
	 *            : int start index of the array
	 * @param toIndex
	 *            : int end index of the array
	 * @return : int random index
	 */
	public static int randomIndexGenerator(int fromIndex, int toIndex) {
		Random rand = new Random();
		int index = rand.nextInt(toIndex - fromIndex + 1) + fromIndex;
		return index;
	}

	/**
	 * swap the two numbers in the array
	 *
	 * @param i
	 *            : int index of the first element to be swapped
	 * @param r
	 *            : int index of the second element to be swapped
	 * @param A
	 *            : int[] array
	 */
	public static void swap(int i, int r, int[] A) {
		int tmp = A[i];
		A[i] = A[r];
		A[r] = tmp;
	}

	public static void main(String[] args) throws FileNotFoundException {
		String filename = "Array.txt";
		PrintWriter f = new PrintWriter(new File(filename));
		Scanner s = new Scanner(new File(filename));
		int sizeOfFile = 1000000000; // 1G
		int memorySize = 1000000; // 1M
		int[] array = new int[memorySize];
		Random rand = new Random();
		for (int i = 0; i < sizeOfFile; i++)
			f.println(rand.nextInt());

		for (int i = 0; i < sizeOfFile; i++) {
			for (int j = 0; j < memorySize; j++) {

			}

		}

	}
}
