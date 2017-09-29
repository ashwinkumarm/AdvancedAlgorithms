package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp5_q3_SelectAlgorithm;

import java.util.PriorityQueue;

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
		
		return null;
	}
	
	
	public static void addElementsToPQ(int A[],PriorityQueue<Integer> q){
		
		for (Integer element : A) {
			q.add(element);
		}
	}
	
	public static void main(String[] args) {
		

	}

}
