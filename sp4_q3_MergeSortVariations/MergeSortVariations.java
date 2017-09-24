package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp4_q3_MergeSortVariations;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort.Timer;

/**
 * This class performs 4 differrent versions of merge sort
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class MergeSortVariations {
	static Integer inf = Integer.MAX_VALUE;

	/**
	 * Performs the merge sort algorithm by creating two tmp arrays in the merge
	 * phase
	 * 
	 * @param A
	 */
	static void mergeSortVersion1(int[] A) {
		mergeSortVersion1(A, 0, A.length - 1);
	}

	/**
	 * Recursively divides the array and performs the merge operation by sorting the
	 * arrays
	 * 
	 * @param A
	 * @param l
	 * @param r
	 */
	private static void mergeSortVersion1(int[] A, int l, int r) {
		if (l < r) {
			int mid = (l + r) / 2;
			mergeSortVersion1(A, l, mid);
			mergeSortVersion1(A, mid + 1, r);
			merge1(A, l, mid, r);
		}
	}

	/**
	 * Merge method sorts the given array with the help of two tmp arrays(L and R)
	 * 
	 * @param A
	 * @param l
	 * @param mid
	 * @param r
	 */
	private static void merge1(int[] A, int l, int mid, int r) {
		int[] L = new int[mid - l + 2];
		int[] R = new int[r - mid + 1];
		L[mid - l + 1] = inf;
		R[r - mid] = inf;
		System.arraycopy(A, l, L, 0, mid - l + 1);
		System.arraycopy(A, mid + 1, R, 0, r - mid);
		int i = 0, j = 0;
		for (int k = l; k <= r; k++) {
			if (L[i] <= R[j])
				A[k] = L[i++];
			else
				A[k] = R[j++];
		}
	}

	/**
	 * Performs the merge sort algorithm by passing the tmp array as an argument to
	 * the merge phase
	 * 
	 * @param A
	 */
	static void mergeSortVersion2(int[] A) {
		mergeSortVersion2(A, 0, A.length - 1);
	}

	/**
	 * Recursively divides the array and performs the merge operation by sorting the
	 * arrays
	 * 
	 * @param A
	 * @param l
	 * @param r
	 */
	private static void mergeSortVersion2(int[] A, int l, int r) {
		if (l < r) {
			int mid = (l + r) / 2;
			mergeSortVersion2(A, l, mid);
			mergeSortVersion2(A, mid + 1, r);
			merge2(A, new int[A.length], l, mid, r);
		}
	}

	/**
	 * 
	 * Merge method sorts the given array with the help of one tmp arrays passed as
	 * an argument
	 * 
	 * @param A
	 * @param tmp
	 * @param l
	 * @param mid
	 * @param r
	 */
	private static void merge2(int[] A, int[] tmp, int l, int mid, int r) {
		System.arraycopy(A, l, tmp, l, r - l + 1);
		int i = l, j = mid + 1;
		for (int k = l; k <= r; k++) {
			if (j > r || (i <= mid && tmp[i] <= tmp[j]))
				A[k] = tmp[i++];
			else
				A[k] = tmp[j++];
		}
	}

	/**
	 * Performs the merge sort algorithm by performing the insertion sort for the
	 * array size less than some threshold else performs the merge sort
	 * 
	 * @param A
	 */
	static void mergeSortVersion3(int A[]) {
		mergeSortVersion3(A, 0, A.length - 1);
	}

	/**
	 * Recursively divides the array and performs the insertion sort for some
	 * threshold or else merge operation by sorting the arrays
	 * 
	 * @param A
	 * @param l
	 * @param r
	 */
	private static void mergeSortVersion3(int[] A, int l, int r) {
		if ((r - l) < 50) {
			insertionSort(A, l, r);
		} else {
			int mid = (l + r) / 2;
			mergeSortVersion3(A, l, mid);
			mergeSortVersion3(A, mid + 1, r);
			merge3(A, new int[A.length], l, mid, r);
		}
	}

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
	 * Merge method sorts the given array with the help of one tmp array passed as
	 * an argument
	 * 
	 * @param A
	 * @param tmp
	 * @param l
	 * @param mid
	 * @param r
	 */
	private static void merge3(int[] A, int[] tmp, int l, int mid, int r) {
		System.arraycopy(A, l, tmp, l, r - l + 1);
		int i = l, j = mid + 1;
		for (int k = l; k <= r; k++) {
			if (j > r || (i <= mid && tmp[i] <= tmp[j]))
				A[k] = tmp[i++];
			else
				A[k] = tmp[j++];
		}
	}

	/**
	 * Performs the merge sort algorithm by performing the insertion sort for the
	 * array size less than some threshold else performs the merge sort without
	 * copying the input array to tmp array
	 * 
	 * @param A
	 */
	static void mergeSortVersion4(int A[]) {
		int B[] = new int[A.length];
		System.arraycopy(A, 0, B, 0, A.length);
		mergeSortVersion4(A, B, 0, A.length - 1);
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
	 * Main method for testing
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String args[]) throws FileNotFoundException {
		Random rand = new Random();
		System.out.println("Enter the size of the array: ");
		Scanner in = new Scanner(System.in);
		int size = in.nextInt();
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = rand.nextInt(1000);
		}
		in.close();
		int[] inputArray = arr;
		Timer t = new Timer();
		mergeSortVersion1(inputArray);
		System.out.println("\nTime and Memory taken by MergeSort Type1:\n" + t.end());
		/*
		 * for(int i=0;i<size;i++) System.out.print(inputArray[i]+" ");
		 */

		inputArray = arr;
		t.start();
		mergeSortVersion2(inputArray);
		System.out.println("\nTime and Memory taken by MergeSort Type2:\n" + t.end());
		/*
		 * for(int i=0;i<size;i++) System.out.print(inputArray[i]+" ");
		 */

		inputArray = arr;
		t.start();
		mergeSortVersion3(inputArray);
		System.out.println("\nTime and Memory taken by MergeSort Type3:\n" + t.end());
		/*
		 * for(int i=0;i<size;i++) System.out.print(inputArray[i]+" ");
		 */

		inputArray = arr;
		t.start();
		mergeSortVersion4(inputArray);
		System.out.println("\nTime and Memory taken by MergeSort Type4:\n" + t.end());
		/*
		 * for(int i=0;i<size;i++) System.out.print(inputArray[i]+" ");
		 */
	}
}
