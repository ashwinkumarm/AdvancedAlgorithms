package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp5_q2_QuickSortDualPivotPartition;

/**
 * Class to implement dual pivot partition Quick Sort
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */

import java.util.Arrays;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort.Shuffle;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort.Timer;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp5_q1_QuickSortPartitions.QuickSortPartitions;

public class DualPivotSort {
	/**
	 * Method that implement dual pivot partitioning (Yaroslavskiy) Algorithm
	 * 
	 * @param A : int[] array
	 * @param p : int start Index of the array
	 * @param r : int end Index of the array
	 */
	public static void dpQuickSort(int A[], int p, int r){
		
		if(p < r){
			
			//randomly choose two pivot elements
			int indexX1 = QuickSortPartitions.randomIndexGenerator(p, r);
			int indexX2 = QuickSortPartitions.randomIndexGenerator(p, r);
			
			QuickSortPartitions.swap(indexX1, p, A);
			QuickSortPartitions.swap(indexX2, r, A);
		
			/*rearrange the pivot elements such that the smallest pivot is on the left end
			 * of the array and the largest pivot is on the right end of the array
			 */
			if(A[p] > A[r]){
				QuickSortPartitions.swap(p, r, A);
			}

		    int i = p + 1; // cursor to keep track of the unprocessed elements
		    int j = r - 1, k = i; 
		    int x1 = A[p], x2 =A[r];
		    
		    while (i <= j) {
		 
		        if (A[i] < x1) {
		            QuickSortPartitions.swap(k, i, A);
		            k++;
		        }
		        else if (A[i] > x2) {
		            while (A[j] > x2 && i < j){
		                j--;
		            }
		            QuickSortPartitions.swap(i, j, A);
		            j--;
		            if (A[k] < x1) {
		            	QuickSortPartitions.swap(k, i, A);
		                k++;
		            }
		        }
		        i++;
		    }
		    k--;
		    j++;
		    
		    // Bring the pivot to the the appropriate index 
		    QuickSortPartitions.swap(p, k, A); 
		    QuickSortPartitions.swap(r, j, A);
		    
		    // recursively call the quickSort on the 3 partitions
			dpQuickSort(A, p, k-1);
			if(x1 != x2){
				dpQuickSort(A, k+1, j-1);
			}
			dpQuickSort(A, j+1, r);
		}
		
	}
	
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		int size = in.nextInt();
		//int[] arr = {2, 8, 8, 9, 1, 3, 29, 31, 3, 6, 7, 4, 31};
		
		int[] arr = new int[size];
		QuickSortPartitions.generateArray(arr, size);
		Shuffle.shuffle(arr);
		System.out.println(Arrays.toString(arr));
		System.out.println("\nQuick Sort With dual pivot");
		System.out.println("_________________________");
		
		Timer t = new Timer();
		dpQuickSort(arr, 0, arr.length - 1);
		t.end();
		System.out.println(Arrays.toString(arr));
		System.out.println(t);
		
		in.close();
	}
	
}
