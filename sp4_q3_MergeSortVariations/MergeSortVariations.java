package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp4_q3_MergeSortVariations;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort.Timer;

public class MergeSortVariations {
	static Integer inf = Integer.MAX_VALUE;

	static void mergeSortAlgorithm1(int[] A) {
		mergeSortAlgorithm1(A, 0, A.length - 1);
	}

	private static void mergeSortAlgorithm1(int[] A, int l, int r) {
		if (l < r) {
			int mid = (l + r) / 2;
			mergeSortAlgorithm1(A, l, mid);
			mergeSortAlgorithm1(A, mid + 1, r);
			merge1(A, l, mid, r);
		}
	}

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

	static void mergeSortAlgorithm2(int[] A) {
		mergeSortAlgorithm2(A, 0, A.length - 1);
	}

	private static void mergeSortAlgorithm2(int[] A, int l, int r) {
		if (l < r) {
			int mid = (l + r) / 2;
			mergeSortAlgorithm2(A, l, mid);
			mergeSortAlgorithm2(A, mid + 1, r);
			merge2(A, new int[A.length], l, mid, r);
		}
	}

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

	static void mergeSortAlgorithm3(int A[]) {
		mergeSortAlgorithm3(A, 0, A.length - 1);
	}

	private static void mergeSortAlgorithm3(int[] A, int l, int r) {
		if ((r - l) < 5) {
			insertionSort(A, l, r);
		} else {
			int mid = (l + r) / 2;
			mergeSortAlgorithm3(A, l, mid);
			mergeSortAlgorithm3(A, mid + 1, r);
			merge3(A, new int[A.length], l, mid, r);
		}
	}

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

	static void mergeSortAlgorithm4(int A[]) {
		int B[] = new int[A.length];
		System.arraycopy(A, 0, B, 0, A.length);
		mergeSortAlgorithm4(A, B, 0, A.length - 1);
	}

	private static void mergeSortAlgorithm4(int[] A, int[] B, int l, int r) {
		if ((r - l) < 5) {
			insertionSort(A, l, r);
		} else {
			int mid = (l + r) / 2;
			mergeSortAlgorithm4(B, A, l, mid);
			mergeSortAlgorithm4(B, A, mid + 1, r);
			merge4(B, A, l, mid, r);
		}
	}

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
			arr[i] = rand.nextInt(500);
		}
		in.close();
		int[] inputArray = arr;
		Timer t = new Timer();
		mergeSortAlgorithm1(inputArray);
		System.out.println("\nTime and Memory taken by MergeSort Type1:\n" + t.end());
		/*for(int i=0;i<size;i++)
			System.out.print(inputArray[i]+" ");*/
		
		inputArray = arr;
		t.start();
		mergeSortAlgorithm2(inputArray);
		System.out.println("\nTime and Memory taken by MergeSort Type2:\n" + t.end());
		/*for(int i=0;i<size;i++)
			System.out.print(inputArray[i]+" ");*/
		
		inputArray = arr;
		t.start();
		mergeSortAlgorithm3(inputArray);
		System.out.println("\nTime and Memory taken by MergeSort Type3:\n" + t.end());
		/*for(int i=0;i<size;i++)
			System.out.print(inputArray[i]+" ");*/
		
		inputArray = arr;
		t.start();
		mergeSortAlgorithm4(inputArray);
		System.out.println("\nTime and Memory taken by MergeSort Type4:\n" + t.end());
		/*for(int i=0;i<size;i++)
			System.out.print(inputArray[i]+" ");*/
	}
}
