package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp5_q1_QuickSortPartitions;

/**
 * Class to implement Quick Sort
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */


import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort.Shuffle;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort.Timer;

public class QuickSortPartitions {
	
	/**
	 * Method implementing Lomuto's partition algorithm
	 * 
	 * @param A : int[] array
	 * @param p : int start Index of the array
	 * @param r : int end Index of the array
	 * @return  : int pivot element
	 */
	public static int partition1(int[] A, int p, int r) {
		
		int i = randomIndexGenerator(p, r);
		swap(i, r, A);
		int x = A[r];
		i = p - 1;
		for (int j = p; j < r; j++) {
			if (A[j] <= x) {
				i++;
				swap(i, j, A);
			}
		}
		swap(i + 1, r, A);
		return i + 1;
	}


	/**
	 * Method implementing Hoare's partition algorithm
	 * 
	 * @param A : int[] array
	 * @param p : int start Index of the array
	 * @param r : int end Index of the array
	 * @return  : int pivot element
	 */
	public static int partition2(int[] A, int p, int r) {
		int i = randomIndexGenerator(p, r);
		int x = A[i];
		i = p - 1;
		int j = r + 1;
		while (true) {
			do {
				i++;
			} while (A[i] < x);
			do {
				j--;
			} while (A[j] > x);
			if (i >= j) {
				return j;
			}
			swap(i, j, A);
		}
	}

	public static void quickSortWithPartition1(int[] A, int p, int r) {
		if (p < r) {
			int q = partition1(A, p, r);
			quickSortWithPartition1(A, p, q - 1);
			quickSortWithPartition1(A, q + 1, r);
		}
	}

	public static void quickSortWithPartition2(int[] A, int p, int r) {
		if (p < r) {
			int q = partition2(A, p, r);
			quickSortWithPartition2(A, p, q);
			quickSortWithPartition2(A, q + 1, r);
		}
	}
	
	/**
	 * swap the two numbers in the array
	 * 
	 * @param i : int index of the first element to be swapped
	 * @param r : int index of the second element to be swapped
	 * @param A : int[] array
	 */
	public static void swap(int i, int r, int[] A) {
		int tmp = A[i];
		A[i] = A[r];
		A[r] = tmp;
	}
	
	/**
	 * Method to generate randon Index from the array
	 * 
	 * @param fromIndex : int start index of the array
	 * @param toIndex : int end index of the array
	 * @return : int random index
	 */
	public static int randomIndexGenerator(int fromIndex, int toIndex) {
		Random rand = new Random();
		int index = rand.nextInt(toIndex - fromIndex + 1) + fromIndex;
		return index;
	}

	public static void generateArray(int arr[], int size) {
		int j = size;
		for (int i = 0; i < size ; i++) {
			arr[i] = j;
			j--;
		}
	}

	
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		int size = in.nextInt();
		int[] arr = new int[size];
		
		generateArray(arr, size);
		Shuffle.shuffle(arr);
		System.out.println(Arrays.toString(arr));
		System.out.println("\nQuick Sort on Randomly ordered Arrays");
		System.out.println("_________________________");
		
		Timer t = new Timer();
		quickSortWithPartition1(arr, 0, arr.length - 1);
		t.end();
		System.out.println("Quick Sort with Patition 1 :");
		System.out.println(Arrays.toString(arr));
		System.out.println(t);
		System.out.println("-------------");

		generateArray(arr, size);
		Shuffle.shuffle(arr);
		
		t = new Timer();
		quickSortWithPartition2(arr, 0, arr.length - 1);
		t.end();
		System.out.println("Quick Sort with Patition 2 :");
		System.out.println(Arrays.toString(arr));
		System.out.println(t);
		System.out.println("-------------");
		System.out.println("\n_________________________");
		
		generateArray(arr, size);
		
		System.out.println(Arrays.toString(arr));
		System.out.println("Quick Sort on Arrays ordered in descending order");
		System.out.println("_________________________");
		t = new Timer();
		quickSortWithPartition1(arr, 0, arr.length - 1);
		t.end();
		System.out.println("Quick Sort with Patition 1 :");
		System.out.println(Arrays.toString(arr));
		System.out.println(t);
		System.out.println("--------------------------");

		generateArray(arr, size);
		
		t = new Timer();
		quickSortWithPartition2(arr, 0, arr.length - 1);
		t.end();
		System.out.println("Quick Sort with Patition 2 :");
		System.out.println(Arrays.toString(arr));
		System.out.println(t);
		in.close();
	}

}
