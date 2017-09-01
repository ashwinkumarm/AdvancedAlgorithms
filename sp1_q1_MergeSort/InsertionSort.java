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
	
	public static void main(String args[]) {
		
		int n = 100000;
	    Integer[] integerArr = new Integer[n];
	    for(int i=0; i<n; i++) {
	    		integerArr[i] = new Integer(i+1);
		}
	    System.out.println("Array Creation Completed");
	    Shuffle.shuffle(integerArr);
//	    System.out.println("Printing Shuffled Array");
//	    for(Integer i:arr)
//			System.out.print(i+"  ");
	    
		Timer t = new Timer();
		nSquareSort(integerArr);
		System.out.println(t.end());
		//for(Integer i:arr)
		//	System.out.print(i+"  ");
		
//		t.start();
//		nSquareSort(doubleArr);
//		System.out.println(t.end());
//		for(Double i:doubleArr)
//			System.out.print(i+"  ");
		
		
	}

}
