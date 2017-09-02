package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort;

import java.util.Random;

public class Shuffle {
	
	 public static<T> void shuffle(T[] arr) {
			shuffle(arr, 0, arr.length-1);
		    }

	 public static<T> void shuffle(T[] arr, int from, int to) {
			int n = to - from  + 1;
			Random rand = new Random();
			for(int i=1; i<n; i++) {
			    int j = rand.nextInt(i);
			    swap(arr, i+from, j+from);
			}
		    }

	static<T> void swap(T[] arr, int x, int y) {
			T tmp = arr[x];
			arr[x] = arr[y];
			arr[y] = tmp;
		    }


}
