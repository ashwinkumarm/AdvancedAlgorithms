package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort;

import java.util.Random;

/**
 * Class InsertionSort
 * 
 * <P> This class performs merge sort on basic 'int' type array 
 * of size ranging from 1M - 16M
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class MergeSortInt {
	
	public static void mergeSort(int[] arr,int[] tmp) {
		divide(arr,tmp,0,arr.length-1);
	}
	
	private static void divide(int[] arr,int[] tmp, int l,int r){ //Divide method to carry out the divide phase of divide and conquer strategy
		if(l<r) {
			int mid = (l+r)/2;
			
			divide(arr,tmp,l,mid);
			divide(arr,tmp,mid+1,r);

			merge(arr,tmp,l,mid,r);
		}	
	}
	
	private static void merge(int[] arr,int[] tmp, int l,int m,int r) { //Merge method to carry out the conquer phase of divide and conquer strategy
		for(int i=0;i<=r;i++)
			tmp[i]=arr[i];
		
		int i=l,j=m+1,k=l;
		
		while(i<=m && j<=r) {
			if (tmp[i] <= tmp[j]) {
				arr[k]=tmp[i];
				i++;
			}
			else {
				arr[k]=tmp[j];
				j++;
			}
			k++;
		}
		while(i<=m) {
			arr[k]=tmp[i];
			k++;
			i++;
		}
		
	}
	
	public static void main(String args[]) {
		int n = 100000;
	    int[] arr = new int[n];
	    for(int i=0; i<n; i++) {
		    arr[i] = i+1;
		}
	  
	    System.out.println("Array Creation Completed");
	    //Shuffling the int Array
	    Random rand = new Random();
	    for (int i = arr.length - 1; i > 0; i--)
	    {
	        int index = rand.nextInt(i + 1);
	        int temp = arr[index];
	        arr[index] = arr[i];
	        arr[i] = temp;
	    }
//	    System.out.println("Printing Shuffled Array: ");
//		for(int i:arr)
//			System.out.print(i+"  ");
	    
		Timer t = new Timer();
		mergeSort(arr,new int[arr.length]);
		System.out.println(t.end());
//		for(int i:arr)
//			System.out.print(i+"  ");
	}

}
