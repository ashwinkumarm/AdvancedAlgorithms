package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp5_q3_SelectAlgorithm;

import java.util.PriorityQueue;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort.Shuffle;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort.Timer;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp5_q1_QuickSortPartitions.QuickSortPartitions;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.MergeSortVariations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Class to implement select Algorithms
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */

public class SelectAlgorithms {
	
	public static final int T = 0;
	
	/**
	 * Select K largest number using priorityQueue (Max Heap)
	 * 
	 * @param A : int[] array
	 * @param k : int k number of largest elements
	 * @return  : List with k largest elements
	 */
	public static List<Integer> priorityQueueMaxHeap(int A[], int k){

		PriorityQueue<Integer> queue = new PriorityQueue<>(A.length, Collections.reverseOrder());
		addElementsToPQ(A, queue);
		List<Integer> kLargestElements =  new ArrayList<>();
		while(k > 0) {
			kLargestElements.add(queue.poll());
			k--;
		}
		return kLargestElements;
	}
	
	/**
	 * Select K largest number using priorityQueue (Min Heap)
	 * 
	 * @param A : int[] array
	 * @param k : int k number of largest elements
	 * @return  : Priority Queue with k largest elements
	 */
	public static PriorityQueue<Integer> priorityQueueMinHeap(int A[], int k){
		
		PriorityQueue<Integer> queue = new PriorityQueue<>(k);
		List<Integer> arrayList = new ArrayList<>();
		for (Integer element : A) {
			arrayList.add(element);
		
		}
		Iterator<Integer> it = arrayList.iterator();
		
		while(k > 0){
			if (it.hasNext()) {
				queue.add(it.next());
			}
			else {
				return queue;
			}
			k--;
		}
		
		while(it.hasNext()){
			int x = it.next();
			if(queue.peek() < x){
				queue.poll();
				queue.add(x);
			}
			
		}
		
		return queue;
	}
	/**
	 * Select K largest number with O(n) runtime
	 * 
	 * @param A : int[] array
	 * @param k : int k number of largest elements
	 * @return  : int[] with k largest elements
	 */
	public static int[] select(int A[], int k){
		
		int n = A.length;
		if( k <= 0 ) { //Base Conditions
			return null;
		}
		if( k > n ) {
			return A;
		}
		select(A, 0, n, k);
		return Arrays.copyOfRange(A, n-k, n);
			
	}
	
	/**
	 * Find kth largest element of A[] Precondition k <= n
	 * 
	 * @param A : int[] array
	 * @param p : int start index of the array
	 * @param n : int length of the array
	 * @param k : int k number of largest elements
	 * @return  : int kth largest element in the array
	 */
	
	public static int select(int A[], int p, int n, int k){
		
		int r = p + (n-1);
		if( n < T){ // if the array length is less than the threshold value do insertion sort
			MergeSortVariations.insertionSort(A, p, r);
			return A[p+n-k];
		}
		else { // else do quick sort
			int q = QuickSortPartitions.partition1(A, p, r);
			int left = q - p;
			int right = r - q;
			if(right >= k){
				return select(A, q+1, right, k);
			}
			else if(right+1 == k){
				return A[q];
			}
			else {
				return select(A, p, left, k-(right+1));
			}
		}
		
	}
	
	public static void addElementsToPQ(int A[],PriorityQueue<Integer> q){
		
		for (Integer element : A) {
			q.add(element);
		}
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
		System.out.println("\nSelection Algorithm: K largest elements in a Array ");
		System.out.println("_________________________");
		
		Timer t = new Timer();
		List<Integer> result1 = priorityQueueMaxHeap(arr, k);
		t.end();
		
		System.out.println("Priority Queue MaxHeap Implementation :");
		System.out.println(Arrays.toString(result1.toArray()));
		System.out.println(t);
		System.out.println("-------------------------");

		generateArray(arr, size);
		Shuffle.shuffle(arr);
		
		t = new Timer();
		PriorityQueue<Integer> result2 = priorityQueueMinHeap(arr, k);
		t.end();
		System.out.println("Priority Queue MinHeap Implementation :");
		System.out.println(Arrays.toString(result2.toArray()));
		System.out.println(t);
		System.out.println("-------------------------");
		
		generateArray(arr, size);
		Shuffle.shuffle(arr);
		
		t = new Timer();
		int result3[] = select(arr, k);
		t.end();
		System.out.println("Selection O(n) algorithm  :");
		System.out.println(Arrays.toString(result3));
		System.out.println(t);
		System.out.println("\n_________________________");
		

	}

}
