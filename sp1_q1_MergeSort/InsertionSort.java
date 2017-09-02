package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort;

/**
 * Class InsertionSort
 * 
 * <P> This class performs insertion sort on a generic array 
 * of size ranging from 1M - 16M
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class InsertionSort {
	
	/**
	 * nSquareSort method gives us the sorted array of elements
	 *  
	 * @param arr generic array which has the original content 
	 */
	public static<T extends Comparable<? super T>> void nSquareSort(T[] arr) {
		for(int i=1;i<arr.length;i++) {
			for(int j=i;j>0;j--) {
				if(arr[j].compareTo(arr[j-1])<0) {
					T tmp=arr[j];
					arr[j]=arr[j-1];
					arr[j-1]=tmp;
				}
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String args[]) {
		
		int n = 100000;
	    Integer[] integerArr = new Integer[n];
	    for(int i=0; i<n; i++) {
	    		integerArr[i] = new Integer(i+1);
		}
	    System.out.println("Array Creation Completed");
	    System.out.println("Shuffling the array and sorting....");
	    Shuffle.shuffle(integerArr);
	    
		Timer t = new Timer();
		nSquareSort(integerArr);
		System.out.println(t.end());
//		for(Integer i:integerArr)
//			System.out.print(i+"  ");
		
	}

}
