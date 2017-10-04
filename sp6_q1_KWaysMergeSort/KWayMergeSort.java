package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q1_KWaysMergeSort;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort.Shuffle;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort.Timer;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.MergeSortVariations;

/**
 * Class to implements Merge sort by splitting the array A 
 * into k fragments, sort them recursively, and merge them
 * using a priority queue
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class KWayMergeSort {

	
	
	public static int[] mergeKSortedArrays(int[][] subA, int k, int sortedArray[]){
		
		PriorityQueue<Integer> queue = new PriorityQueue<>();
		int subArraySize = subA[0].length;
		int l = 0;
		for(int j=0; j< subArraySize; j++) {
			int i = 0;
			while(i < k) {
				if(queue.size() < k) {
					queue.add(subA[i][j]);
					i++;
				}
				else {
					sortedArray[l] = queue.poll();
					l++;
				}				
			}
			
		}
		while(!queue.isEmpty()){
			sortedArray[l] = queue.poll();
			l++;
		}
		return sortedArray;
		
	}
	
	public static int[][] fragmentArray(int A[],int k){
		
		int subArraySize = A.length/k;
		if(A.length%k != 0){
			k = k+1;
		}
		
		int subA[][] = new int[k][];
		int l = 0;
		for(int i=0; i<k; i++){
			for(int j =0; j<subArraySize ; j++ ){
				subA[i][j] = A[l];
				l++;
			}
		}
		return subA;
	}
	
	public static int[] kWaysMergeSort(int A[], int k){
		int subA[][] = fragmentArray(A, k);
		for (int[] array : subA) {
			MergeSortVariations.mergeSortVersion4(array);
		}
		int sortedArray[] = new int[A.length]; 
		mergeKSortedArrays(subA, k, sortedArray);
		return sortedArray;
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
		int k = in.nextInt();
		int[] arr = new int[size];
		
		generateArray(arr, size);
		Shuffle.shuffle(arr);
		System.out.println(Arrays.toString(arr));
		System.out.println("\nMerge Sort K ways");
		
		Timer t = new Timer();
		int[] a = kWaysMergeSort(arr, k);
		t.end();
		System.out.println(Arrays.toString(a));
		System.out.println(t);
		in.close();

	}

}
